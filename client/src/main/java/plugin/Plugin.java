package plugin;

import plugin.api.MiniMenuEntry;
import rt4.Component;
import rt4.Npc;
import rt4.Player;

/**
 * Base class for Killer Edition client plugins.
 *
 * startUp()/shutDown() are the preferred modern lifecycle. The historic
 * callbacks remain available so existing plugin source can migrate gradually.
 */
public abstract class Plugin {
    private long timeOfLastDraw;
    private long timeOfLastLateDraw;

    final void _startUp() throws Exception {
        startUp();
        Init();
    }

    final void _shutDown() throws Exception {
        shutDown();
    }

    final void _draw() {
        long nowTime = System.currentTimeMillis();
        Draw(nowTime - timeOfLastDraw);
        timeOfLastDraw = nowTime;
    }

    final void _lateDraw() {
        long nowTime = System.currentTimeMillis();
        LateDraw(nowTime - timeOfLastLateDraw);
        timeOfLastLateDraw = nowTime;
    }

    protected void startUp() throws Exception {}
    protected void shutDown() throws Exception {}

    public void Draw(long timeDelta) {}
    public void LateDraw(long timeDelta) {}
    public void Init() {}
    public void OnXPUpdate(int skill, int xp) {}
    public void Update() {}
    public void PlayerOverheadDraw(Player player, int screenX, int screenY) {}
    public void NPCOverheadDraw(Npc npc, int screenX, int screenY) {}
    public void ProcessCommand(String commandStr, String[] args) {}
    public void ComponentDraw(int componentIndex, Component component, int screenX, int screenY) {}
    public void OnVarpUpdate(int id, int value) {}
    public void OnLogin() {}
    public void OnKillingBlowNPC(int npcID, int x, int z) {}
    public void OnLogout() {}
    public boolean OnPluginsReloaded() { return false; }
    public void DrawMiniMenu(MiniMenuEntry entry) {}
    public void OnMiniMenuCreate(MiniMenuEntry[] currentEntries) {}
}
