package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ei")
public final class MixerPcmStream extends PcmStream {

	@OriginalMember(owner = "client!ei", name = "t", descriptor = "Lclient!ih;")
	private final LinkedList subStreams = new LinkedList();

	@OriginalMember(owner = "client!ei", name = "u", descriptor = "Lclient!ih;")
	private final LinkedList listeners = new LinkedList();

	@OriginalMember(owner = "client!ei", name = "v", descriptor = "I")
	private int samplePosition = 0;

	@OriginalMember(owner = "client!ei", name = "w", descriptor = "I")
	private int nextListenerTime = -1;

	@OriginalMember(owner = "client!ok", name = "a", descriptor = "(Lclient!ab;Lclient!ab;I)V")
	public static void insertBefore(@OriginalArg(0) Node node, @OriginalArg(1) Node before) {
		if (node.previousNode != null) {
			node.unlink();
		}
		node.nextNode = before;
		node.previousNode = before.previousNode;
		node.previousNode.nextNode = node;
		node.nextNode.previousNode = node;
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(Lclient!cc;)V")
	private void removeListener(@OriginalArg(0) MixerListener listener) {
		listener.unlink();
		listener.onRemoved();
		@Pc(9) Node first = this.listeners.sentinel.nextNode;
		if (first == this.listeners.sentinel) {
			this.nextListenerTime = -1;
		} else {
			this.nextListenerTime = ((MixerListener) first).delay;
		}
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(Lclient!qb;)V")
	public final synchronized void addSubStream(@OriginalArg(0) PcmStream stream) {
		this.subStreams.addHead(stream);
	}

	@OriginalMember(owner = "client!ei", name = "b", descriptor = "([III)V")
	@Override
	public final synchronized void read(@OriginalArg(0) int[] buf, @OriginalArg(1) int offset, @OriginalArg(2) int len) {
		do {
			if (this.nextListenerTime < 0) {
				this.readSubStreams(buf, offset, len);
				return;
			}
			if (this.samplePosition + len < this.nextListenerTime) {
				this.samplePosition += len;
				this.readSubStreams(buf, offset, len);
				return;
			}
			@Pc(33) int samplesUntilListener = this.nextListenerTime - this.samplePosition;
			this.readSubStreams(buf, offset, samplesUntilListener);
			offset += samplesUntilListener;
			len -= samplesUntilListener;
			this.samplePosition += samplesUntilListener;
			this.advanceListeners();
			@Pc(60) MixerListener listener = (MixerListener) this.listeners.head();
			synchronized (listener) {
				@Pc(68) int result = listener.process(this);
				if (result < 0) {
					listener.delay = 0;
					this.removeListener(listener);
				} else {
					listener.delay = result;
					this.insertListenerSorted(listener.nextNode, listener);
				}
			}
		} while (len != 0);
	}

	@OriginalMember(owner = "client!ei", name = "e", descriptor = "()V")
	private void advanceListeners() {
		if (this.samplePosition <= 0) {
			return;
		}
		for (@Pc(8) MixerListener listener = (MixerListener) this.listeners.head(); listener != null; listener = (MixerListener) this.listeners.next()) {
			listener.delay -= this.samplePosition;
		}
		this.nextListenerTime -= this.samplePosition;
		this.samplePosition = 0;
	}

	@OriginalMember(owner = "client!ei", name = "b", descriptor = "()Lclient!qb;")
	@Override
	public final PcmStream firstSubStream() {
		return (PcmStream) this.subStreams.head();
	}

	@OriginalMember(owner = "client!ei", name = "d", descriptor = "(I)V")
	private void skipSubStreams(@OriginalArg(0) int len) {
		for (@Pc(5) PcmStream stream = (PcmStream) this.subStreams.head(); stream != null; stream = (PcmStream) this.subStreams.next()) {
			stream.skip(len);
		}
	}

	@OriginalMember(owner = "client!ei", name = "c", descriptor = "([III)V")
	private void readSubStreams(@OriginalArg(0) int[] buf, @OriginalArg(1) int offset, @OriginalArg(2) int len) {
		for (@Pc(5) PcmStream stream = (PcmStream) this.subStreams.head(); stream != null; stream = (PcmStream) this.subStreams.next()) {
			stream.readIfActive(buf, offset, len);
		}
	}

	@OriginalMember(owner = "client!ei", name = "b", descriptor = "(Lclient!qb;)V")
	public final synchronized void removeSubStream(@OriginalArg(0) PcmStream stream) {
		stream.unlink();
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "()I")
	@Override
	public final int getActiveChannelCount() {
		return 0;
	}

	@OriginalMember(owner = "client!ei", name = "c", descriptor = "(I)V")
	@Override
	public final synchronized void skip(@OriginalArg(0) int len) {
		do {
			if (this.nextListenerTime < 0) {
				this.skipSubStreams(len);
				return;
			}
			if (this.samplePosition + len < this.nextListenerTime) {
				this.samplePosition += len;
				this.skipSubStreams(len);
				return;
			}
			@Pc(29) int samplesUntilListener = this.nextListenerTime - this.samplePosition;
			this.skipSubStreams(samplesUntilListener);
			len -= samplesUntilListener;
			this.samplePosition += samplesUntilListener;
			this.advanceListeners();
			@Pc(50) MixerListener listener = (MixerListener) this.listeners.head();
			synchronized (listener) {
				@Pc(58) int result = listener.process(this);
				if (result < 0) {
					listener.delay = 0;
					this.removeListener(listener);
				} else {
					listener.delay = result;
					this.insertListenerSorted(listener.nextNode, listener);
				}
			}
		} while (len != 0);
	}

	@OriginalMember(owner = "client!ei", name = "d", descriptor = "()Lclient!qb;")
	@Override
	public final PcmStream nextSubStream() {
		return (PcmStream) this.subStreams.next();
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(Lclient!ab;Lclient!cc;)V")
	private void insertListenerSorted(@OriginalArg(0) Node cursor, @OriginalArg(1) MixerListener listener) {
		while (cursor != this.listeners.sentinel && ((MixerListener) cursor).delay <= listener.delay) {
			cursor = cursor.nextNode;
		}
		insertBefore(listener, cursor);
		this.nextListenerTime = ((MixerListener) this.listeners.sentinel.nextNode).delay;
	}
}
