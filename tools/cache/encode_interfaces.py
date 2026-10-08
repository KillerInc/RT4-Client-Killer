#!/usr/bin/env python3
import argparse, json, pathlib, struct, sys

HOOK_ORDER = [
    'onLoad','onMouseOver','onMouseLeave','onUseWith','onUse',
    'onVarpTransmit','onInvTransmit','onStatTransmit','onTimer','onOptionClick',
    'onMouseRepeat','onClickRepeat','onDrag','onRelease','onHold',
    'onDragStart','onDragRelease','onScroll','onVarcTransmit','onVarcstrTransmit'
]
TRIGGER_ORDER = ['varpTriggers','inventoryTriggers','statTriggers','varcTriggers','varcstrTriggers']

class W:
    def __init__(self): self.b=bytearray()
    def p1(self,v): self.b.append(v & 0xff)
    def p2(self,v): self.b.extend(struct.pack('>H', v & 0xffff))
    def p2s(self,v): self.b.extend(struct.pack('>h', int(v)))
    def p3(self,v): self.b.extend(((v>>16)&255,(v>>8)&255,v&255))
    def p4(self,v): self.b.extend(struct.pack('>I', v & 0xffffffff))
    def pjstr(self,s):
        if s is None: s=''
        self.b.extend(str(s).encode('cp1252'))
        self.b.append(0)
    def bytes(self): return bytes(self.b)

def parent_child_id(d, key='overlayer'):
    v=d.get(key,-1)
    if v is None or v < 0: return 65535
    return int(v) & 0xffff

def encode_handler(w, values):
    if not values:
        w.p1(0); return
    if len(values)>255: raise ValueError('hook has >255 args')
    w.p1(len(values))
    for v in values:
        if isinstance(v,str):
            w.p1(1); w.pjstr(v)
        elif isinstance(v,bool):
            w.p1(0); w.p4(int(v))
        elif isinstance(v,int):
            w.p1(0); w.p4(v)
        else:
            raise TypeError(f'unsupported hook arg {v!r}')

def encode_triggers(w, values):
    if not values:
        w.p1(0); return
    if len(values)>255: raise ValueError('trigger list has >255 items')
    w.p1(len(values))
    for v in values: w.p4(v)

def encode_if3(d):
    w=W()
    w.p1(d.get('marker',255))
    w.p1(d['type'])
    w.p2(d.get('clientCode',0))
    w.p2s(d.get('baseX',0)); w.p2s(d.get('baseY',0))
    w.p2(d.get('baseWidth',0)); w.p2(d.get('baseHeight',0))
    w.p1(d.get('dynamicWidthValue',0)); w.p1(d.get('dynamicHeightValue',0))
    w.p1(d.get('yMode',0)); w.p1(d.get('xMode',0))
    w.p2(parent_child_id(d))
    w.p1(1 if d.get('hidden',False) else 0)
    t=d['type']
    if t==0:
        w.p2(d.get('scrollMaxH',0)); w.p2(d.get('scrollMaxV',0)); w.p1(1 if d.get('noClickThrough',False) else 0)
    if t==5:
        w.p4(d.get('spriteId',-1)); w.p2(d.get('angle2d',0))
        flags=d.get('spriteFlags')
        if flags is None:
            flags=(1 if d.get('spriteTiling',False) else 0) | (2 if d.get('hasAlpha',False) else 0)
        w.p1(flags)
        w.p1(d.get('alpha',0)); w.p1(d.get('outlineThickness',0)); w.p4(d.get('shadowColor',0))
        w.p1(1 if d.get('vFlip',False) else 0); w.p1(1 if d.get('hFlip',False) else 0)
    if t==6:
        w.p2(65535 if d.get('modelId',-1)<0 else d.get('modelId',-1))
        w.p2s(d.get('modelXOffset',0)); w.p2s(d.get('modelYOffset',0))
        w.p2(d.get('modelXAngle',0)); w.p2(d.get('modelYAngle',0)); w.p2(d.get('modelZAngle',0)); w.p2(d.get('modelZoom',0))
        w.p2(65535 if d.get('modelSeqId',-1)<0 else d.get('modelSeqId',-1))
        w.p1(1 if d.get('modelOrtho',False) else 0)
        w.p2(d.get('modelExtra1',0)); w.p2(d.get('modelExtra2',0)); w.p1(1 if d.get('modelFlag',False) else 0)
        if d.get('dynamicWidthValue',0)!=0: w.p2(d.get('modelDynamicWidth',0))
        if d.get('dynamicHeightValue',0)!=0: w.p2(d.get('modelDynamicHeight',0))
    if t==4:
        w.p2(65535 if d.get('font',-1)<0 else d.get('font',-1))
        w.pjstr(d.get('text',''))
        w.p1(d.get('vpadding',0)); w.p1(d.get('halign',0)); w.p1(d.get('valign',0)); w.p1(1 if d.get('shadowed',False) else 0)
        w.p4(d.get('color',0))
    if t==3:
        w.p4(d.get('color',0)); w.p1(1 if d.get('filled',False) else 0); w.p1(d.get('alpha',0))
    if t==9:
        w.p1(d.get('lineWidth',1)); w.p4(d.get('color',0)); w.p1(1 if d.get('lineDirection',False) else 0)

    events=d.get('events',0); w.p3(events)

    kb=d.get('keyBindings') or []
    if kb:
        ordered=sorted(kb,key=lambda x:int(x['slot']))
        for k in ordered:
            slot=int(k['slot']); delay=int(k.get('value',-1))
            packed=4095 if delay<0 else delay & 0xfff
            first=((slot+1)<<4) | ((packed>>8)&0xf)
            w.p1(first); w.p1(packed & 0xff); w.p1(int(k.get('byte1',0))); w.p1(int(k.get('byte2',0)))
        w.p1(0)
    else:
        w.p1(0)

    w.pjstr(d.get('optionBase',''))
    ops=d.get('ops') or []
    drag_targets=d.get('dragTargets') or []
    drag_count=min(len(drag_targets),2)
    if len(ops)>15: raise ValueError(f"too many ops: {len(ops)}")
    w.p1((drag_count<<4) | len(ops))
    for op in ops: w.pjstr(op or '')
    if drag_count:
        for idx,val in drag_targets[:drag_count]:
            w.p1(idx); w.p2(val)
    w.p1(d.get('dragDeadzone',0)); w.p1(d.get('dragDeadtime',0)); w.p1(1 if d.get('dragRenderBehavior',False) else 0)
    w.pjstr(d.get('optionCircumfix',''))
    if d.get('targetMask', (events>>11)&0x7f) != 0:
        w.p2(65535 if d.get('targetParam',-1)<0 else d.get('targetParam',-1))
        w.p2(65535 if d.get('targetOverCursor',-1)<0 else d.get('targetOverCursor',-1))
        w.p2(65535 if d.get('targetLeaveCursor',-1)<0 else d.get('targetLeaveCursor',-1))

    hooks=d.get('hooks') or {}
    for name in HOOK_ORDER: encode_handler(w,hooks.get(name))
    triggers=d.get('triggers') or {}
    for name in TRIGGER_ORDER: encode_triggers(w,triggers.get(name))
    return w.bytes()

def encode_if1(d):
    w=W(); t=d['type']; bt=d.get('buttonType',0); events=d.get('events',0)
    w.p1(t); w.p1(bt); w.p2(d.get('clientCode',0)); w.p2s(d.get('baseX',0)); w.p2s(d.get('baseY',0)); w.p2(d.get('baseWidth',0)); w.p2(d.get('baseHeight',0)); w.p1(d.get('alpha',0)); w.p2(parent_child_id(d)); w.p2(65535 if d.get('anInt470',-1)<0 else d.get('anInt470',-1))
    comps=d.get('cs1Comparisons') or []
    w.p1(len(comps))
    for c in comps: w.p1(c['opcode']); w.p2(c['operand'])
    scripts=d.get('cs1Scripts') or []
    w.p1(len(scripts))
    for s in scripts:
        w.p2(len(s))
        for v in s: w.p2(65535 if v<0 else v)
    if t==0:
        w.p2(d.get('scrollMaxV',0)); w.p1(1 if d.get('hidden',False) else 0)
    if t==1:
        w.p2(d.get('_type1UnknownShort',0)); w.p1(d.get('_type1UnknownByte',0))
    if t==2:
        w.p1(1 if events & 0x10000000 else 0)
        w.p1(1 if events & 0x40000000 else 0)
        w.p1(1 if events & 0x80000000 else 0)
        w.p1(1 if events & 0x20000000 else 0)
        w.p1(d.get('invMarginX',0)); w.p1(d.get('invMarginY',0))
        sprite_by_slot={int(x['slot']):x for x in (d.get('inventorySprites') or [])}
        for i in range(20):
            x=sprite_by_slot.get(i)
            if x is None:
                w.p1(0)
            else:
                w.p1(1); w.p2s(x.get('x',0)); w.p2s(x.get('y',0)); w.p4(x.get('sprite',-1))
        opts=d.get('inventoryOptions') or []
        for i in range(5): w.pjstr(opts[i] if i<len(opts) else '')
    if t==3: w.p1(1 if d.get('filled',False) else 0)
    if t in (4,1):
        w.p1(d.get('halign',0)); w.p1(d.get('valign',0)); w.p1(d.get('vpadding',0)); w.p2(65535 if d.get('font',-1)<0 else d.get('font',-1)); w.p1(1 if d.get('shadowed',False) else 0)
    if t==4:
        w.pjstr(d.get('text','')); w.pjstr(d.get('activeText',''))
    if t in (1,3,4): w.p4(d.get('color',0))
    if t in (3,4):
        w.p4(d.get('activeColor',0)); w.p4(d.get('overColor',0)); w.p4(d.get('activeOverColor',0))
    if t==5:
        w.p4(d.get('spriteId',-1)); w.p4(d.get('activeSpriteId',-1))
    if t==6:
        w.p2(65535 if d.get('modelId',-1)<0 else d.get('modelId',-1)); w.p2(65535 if d.get('activeModelId',-1)<0 else d.get('activeModelId',-1)); w.p2(65535 if d.get('modelSeqId',-1)<0 else d.get('modelSeqId',-1)); w.p2(65535 if d.get('activeModelSeqId',-1)<0 else d.get('activeModelSeqId',-1)); w.p2(d.get('modelZoom',0)); w.p2(d.get('modelXAngle',0)); w.p2(d.get('modelYAngle',0))
    if t==7:
        w.p1(d.get('halign',0)); w.p2(65535 if d.get('font',-1)<0 else d.get('font',-1)); w.p1(1 if d.get('shadowed',False) else 0); w.p4(d.get('color',0)); w.p2s(d.get('invMarginX',0)); w.p2s(d.get('invMarginY',0)); w.p1(1 if events & 0x40000000 else 0)
        opts=d.get('inventoryOptions') or []
        for i in range(5): w.pjstr(opts[i] if i<len(opts) else '')
    if t==8: w.pjstr(d.get('text',''))
    if bt==2 or t==2:
        w.pjstr(d.get('optionCircumfix','')); w.pjstr(d.get('optionSuffix','')); w.p2((events>>11)&0x3f)
    if bt in (1,4,5,6): w.pjstr(d.get('option',''))
    return w.bytes()

def encode_component(d):
    if d['format']=='IF3': return encode_if3(d)
    if d['format']=='IF1': return encode_if1(d)
    raise ValueError(d.get('format'))

def main():
    ap=argparse.ArgumentParser(description='Encode Killer Edition decoded interface JSON back to RT4 IF1/IF3 component bytes.')
    ap.add_argument('json_file',type=pathlib.Path)
    ap.add_argument('output_file',type=pathlib.Path)
    ap.add_argument('--verify-against',type=pathlib.Path)
    args=ap.parse_args()
    d=json.loads(args.json_file.read_text(encoding='utf-8'))
    out=encode_component(d)
    args.output_file.parent.mkdir(parents=True,exist_ok=True); args.output_file.write_bytes(out)
    if args.verify_against:
        expected=args.verify_against.read_bytes()
        if out!=expected:
            n=min(len(out),len(expected)); first=next((i for i in range(n) if out[i]!=expected[i]), n if len(out)!=len(expected) else -1)
            print(f'MISMATCH encoded={len(out)} expected={len(expected)} firstDiff={first}',file=sys.stderr)
            return 2
        print(f'OK byte-identical {len(out)} bytes')
    else:
        print(f'encoded {len(out)} bytes -> {args.output_file}')
    return 0
if __name__=='__main__': raise SystemExit(main())
