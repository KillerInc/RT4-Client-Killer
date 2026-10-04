package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;

public class MusicPlayer {
	@OriginalMember(owner = "client!s", name = "c", descriptor = "I")
	public static int groupId = -1;
	@OriginalMember(owner = "client!uf", name = "m", descriptor = "I")
	public static int titleSong;

	@OriginalMember(owner = "client!li", name = "a", descriptor = "(ZI)V")
	public static void playSong(@OriginalArg(1) int songId) {
		if (songId == -1 && !MidiPlayer.jingle) {
			MidiPlayer.stop();
		} else if (songId != -1 && (groupId != songId || !MidiPlayer.isPlaying()) && Preferences.musicVolume != 0 && !MidiPlayer.jingle) {
			MidiPlayer.playFadeOut(songId, client.js5Archive6, Preferences.musicVolume);
		}
		groupId = songId;
	}

	@OriginalMember(owner = "client!wj", name = "a", descriptor = "(IIB)V")
	public static void playJingle(@OriginalArg(0) int length, @OriginalArg(1) int jingleGroupId) {
		if (Preferences.musicVolume != 0 && jingleGroupId != -1) {
			MidiPlayer.playImmediate(client.js5Archive11, jingleGroupId, Preferences.musicVolume);
			MidiPlayer.jingle = true;
		}
	}
}
