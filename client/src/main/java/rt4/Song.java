package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!rf")
public final class Song extends Node {

	@OriginalMember(owner = "client!rf", name = "p", descriptor = "Lclient!sc;")
	public HashTable programs;

	@OriginalMember(owner = "client!rf", name = "q", descriptor = "[B")
	public final byte[] midiBytes;

	@OriginalMember(owner = "client!rf", name = "<init>", descriptor = "(Lclient!wa;)V")
	public Song(@OriginalArg(0) Buffer in) {
		in.offset = in.data.length - 3;
		@Pc(12) int tracks = in.g1();
		@Pc(16) int division = in.g2();
		@Pc(22) int midiLen = tracks * 10 + 14;
		in.offset = 0;
		@Pc(27) int tempoChanges = 0;
		@Pc(29) int controllerEvents = 0;
		@Pc(31) int noteOnEvents = 0;
		@Pc(33) int noteOffEvents = 0;
		@Pc(35) int pitchWheelEvents = 0;
		@Pc(37) int channelPressureEvents = 0;
		@Pc(39) int keyPressureEvents = 0;
		@Pc(41) int bankSelectEvents = 0;
		@Pc(43) int deltaTimePos;
		@Pc(48) int status;
		@Pc(52) int statusAndChannel;
		for (deltaTimePos = 0; deltaTimePos < tracks; deltaTimePos++) {
			status = -1;
			while (true) {
				statusAndChannel = in.g1();
				if (statusAndChannel != status) {
					midiLen++;
				}
				status = statusAndChannel & 0xF;
				if (statusAndChannel == 7) {
					break;
				}
				if (statusAndChannel == 23) {
					tempoChanges++;
				} else if (status == 0) {
					noteOnEvents++;
				} else if (status == 1) {
					noteOffEvents++;
				} else if (status == 2) {
					controllerEvents++;
				} else if (status == 3) {
					pitchWheelEvents++;
				} else if (status == 4) {
					channelPressureEvents++;
				} else if (status == 5) {
					keyPressureEvents++;
				} else if (status == 6) {
					bankSelectEvents++;
				} else {
					throw new RuntimeException();
				}
			}
		}
		midiLen += tempoChanges * 5;
		midiLen += (noteOnEvents + noteOffEvents + controllerEvents + pitchWheelEvents + keyPressureEvents) * 2;
		midiLen += channelPressureEvents + bankSelectEvents;
		deltaTimePos = in.offset;
		status = tracks + tempoChanges + controllerEvents + noteOnEvents + noteOffEvents + pitchWheelEvents + channelPressureEvents + keyPressureEvents + bankSelectEvents;
		for (statusAndChannel = 0; statusAndChannel < status; statusAndChannel++) {
			in.gVarInt();
		}
		midiLen += in.offset - deltaTimePos;
		statusAndChannel = in.offset;
		@Pc(179) int modulationWheelMsbEvents = 0;
		@Pc(181) int modulationWheelLsbEvents = 0;
		@Pc(183) int channelVolumeMsbEvents = 0;
		@Pc(185) int channelVolumeLsbEvents = 0;
		@Pc(187) int panMsbEvents = 0;
		@Pc(189) int panLsbEvents = 0;
		@Pc(191) int nonRegisteredMsbEvents = 0;
		@Pc(193) int nonRegisteredLsbEvents = 0;
		@Pc(195) int registeredMsbEvents = 0;
		@Pc(197) int registeredLsbEvents = 0;
		@Pc(199) int otherKnownControllerEvents = 0;
		@Pc(201) int unknownControllerEvents = 0;
		@Pc(203) int controller = 0;
		@Pc(205) int i;
		for (i = 0; i < controllerEvents; i++) {
			controller = controller + in.g1() & 0x7F;
			if (controller == 0 || controller == 32) {
				bankSelectEvents++;
			} else if (controller == 1) {
				modulationWheelMsbEvents++;
			} else if (controller == 33) {
				modulationWheelLsbEvents++;
			} else if (controller == 7) {
				channelVolumeMsbEvents++;
			} else if (controller == 39) {
				channelVolumeLsbEvents++;
			} else if (controller == 10) {
				panMsbEvents++;
			} else if (controller == 42) {
				panLsbEvents++;
			} else if (controller == 99) {
				nonRegisteredMsbEvents++;
			} else if (controller == 98) {
				nonRegisteredLsbEvents++;
			} else if (controller == 101) {
				registeredMsbEvents++;
			} else if (controller == 100) {
				registeredLsbEvents++;
			} else if (controller == 64 || controller == 65 || controller == 120 || controller == 121 || controller == 123) {
				otherKnownControllerEvents++;
			} else {
				unknownControllerEvents++;
			}
		}
		i = 0;
		@Pc(298) int otherKnownControllerPos = in.offset;
		in.offset += otherKnownControllerEvents;
		@Pc(307) int keyPressurePos = in.offset;
		in.offset += keyPressureEvents;
		@Pc(316) int channelPressurePos = in.offset;
		in.offset += channelPressureEvents;
		@Pc(325) int pitchWheelMsbPos = in.offset;
		in.offset += pitchWheelEvents;
		@Pc(334) int modulationWheelMsbPos = in.offset;
		in.offset += modulationWheelMsbEvents;
		@Pc(343) int channelVolumeMsbPos = in.offset;
		in.offset += channelVolumeMsbEvents;
		@Pc(352) int panMsbPos = in.offset;
		in.offset += panMsbEvents;
		@Pc(361) int keyPos = in.offset;
		in.offset += noteOnEvents + noteOffEvents + keyPressureEvents;
		@Pc(374) int onVelocityPos = in.offset;
		in.offset += noteOnEvents;
		@Pc(383) int unknownControllerPos = in.offset;
		in.offset += unknownControllerEvents;
		@Pc(392) int offVelocityPos = in.offset;
		in.offset += noteOffEvents;
		@Pc(401) int modulationWheelLsbPos = in.offset;
		in.offset += modulationWheelLsbEvents;
		@Pc(410) int channelVolumeLsbPos = in.offset;
		in.offset += channelVolumeLsbEvents;
		@Pc(419) int panLsbPos = in.offset;
		in.offset += panLsbEvents;
		@Pc(428) int bankSelectPos = in.offset;
		in.offset += bankSelectEvents;
		@Pc(437) int pitchWheelLsbPos = in.offset;
		in.offset += pitchWheelEvents;
		@Pc(446) int nonRegisteredMsbPos = in.offset;
		in.offset += nonRegisteredMsbEvents;
		@Pc(455) int nonRegisteredLsbPos = in.offset;
		in.offset += nonRegisteredLsbEvents;
		@Pc(464) int registeredMsbPos = in.offset;
		in.offset += registeredMsbEvents;
		@Pc(473) int registeredLsbPos = in.offset;
		in.offset += registeredLsbEvents;
		@Pc(482) int tempoPos = in.offset;
		in.offset += tempoChanges * 3;
		this.midiBytes = new byte[midiLen];
		@Pc(500) Buffer out = new Buffer(this.midiBytes);
		out.p4(1297377380);
		out.p4(6);
		out.p2(tracks > 1 ? 1 : 0);
		out.p2(tracks);
		out.p2(division);
		in.offset = deltaTimePos;
		@Pc(530) int channel = 0;
		@Pc(532) int keyDelta = 0;
		@Pc(534) int onVelocityDelta = 0;
		@Pc(536) int offVelocityDelta = 0;
		@Pc(538) int pitchWheelDelta = 0;
		@Pc(540) int channelPressureDelta = 0;
		@Pc(542) int keyPressureDelta = 0;
		@Pc(545) int[] values = new int[128];
		controller = 0;
		track:
		for (@Pc(549) int t = 0; t < tracks; t++) {
			out.p4(1297379947);
			out.offset += 4;
			@Pc(565) int trackStart = out.offset;
			@Pc(567) int statusType = -1;
			while (true) {
				while (true) {
					@Pc(571) int deltaTime = in.gVarInt();
					out.pVarInt(deltaTime);
					@Pc(583) int eventByte = in.data[i++] & 0xFF;
					@Pc(590) boolean statusChanged = eventByte != statusType;
					statusType = eventByte & 0xF;
					if (eventByte == 7) {
						if (statusChanged) {
							out.p1(255);
						}
						out.p1(47);
						out.p1(0);
						out.psize4(out.offset - trackStart);
						continue track;
					}
					if (eventByte == 23) {
						if (statusChanged) {
							out.p1(255);
						}
						out.p1(81);
						out.p1(3);
						out.p1(in.data[tempoPos++]);
						out.p1(in.data[tempoPos++]);
						out.p1(in.data[tempoPos++]);
					} else {
						channel ^= eventByte >> 4;
						if (statusType == 0) {
							if (statusChanged) {
								out.p1(channel + 144);
							}
							keyDelta += in.data[keyPos++];
							onVelocityDelta += in.data[onVelocityPos++];
							out.p1(keyDelta & 0x7F);
							out.p1(onVelocityDelta & 0x7F);
						} else if (statusType == 1) {
							if (statusChanged) {
								out.p1(channel + 128);
							}
							keyDelta += in.data[keyPos++];
							offVelocityDelta += in.data[offVelocityPos++];
							out.p1(keyDelta & 0x7F);
							out.p1(offVelocityDelta & 0x7F);
						} else if (statusType == 2) {
							if (statusChanged) {
								out.p1(channel + 176);
							}
							controller = controller + in.data[statusAndChannel++] & 0x7F;
							out.p1(controller);
							@Pc(775) byte valueDelta;
							if (controller == 0 || controller == 32) {
								valueDelta = in.data[bankSelectPos++];
							} else if (controller == 1) {
								valueDelta = in.data[modulationWheelMsbPos++];
							} else if (controller == 33) {
								valueDelta = in.data[modulationWheelLsbPos++];
							} else if (controller == 7) {
								valueDelta = in.data[channelVolumeMsbPos++];
							} else if (controller == 39) {
								valueDelta = in.data[channelVolumeLsbPos++];
							} else if (controller == 10) {
								valueDelta = in.data[panMsbPos++];
							} else if (controller == 42) {
								valueDelta = in.data[panLsbPos++];
							} else if (controller == 99) {
								valueDelta = in.data[nonRegisteredMsbPos++];
							} else if (controller == 98) {
								valueDelta = in.data[nonRegisteredLsbPos++];
							} else if (controller == 101) {
								valueDelta = in.data[registeredMsbPos++];
							} else if (controller == 100) {
								valueDelta = in.data[registeredLsbPos++];
							} else if (controller == 64 || controller == 65 || controller == 120 || controller == 121 || controller == 123) {
								valueDelta = in.data[otherKnownControllerPos++];
							} else {
								valueDelta = in.data[unknownControllerPos++];
							}
							@Pc(910) int controllerValue = valueDelta + values[controller];
							values[controller] = controllerValue;
							out.p1(controllerValue & 0x7F);
						} else if (statusType == 3) {
							if (statusChanged) {
								out.p1(channel + 224);
							}
							pitchWheelDelta += in.data[pitchWheelLsbPos++];
							pitchWheelDelta += in.data[pitchWheelMsbPos++] << 7;
							out.p1(pitchWheelDelta & 0x7F);
							out.p1(pitchWheelDelta >> 7 & 0x7F);
						} else if (statusType == 4) {
							if (statusChanged) {
								out.p1(channel + 208);
							}
							channelPressureDelta += in.data[channelPressurePos++];
							out.p1(channelPressureDelta & 0x7F);
						} else if (statusType == 5) {
							if (statusChanged) {
								out.p1(channel + 160);
							}
							keyDelta += in.data[keyPos++];
							keyPressureDelta += in.data[keyPressurePos++];
							out.p1(keyDelta & 0x7F);
							out.p1(keyPressureDelta & 0x7F);
						} else if (statusType == 6) {
							if (statusChanged) {
								out.p1(channel + 192);
							}
							out.p1(in.data[bankSelectPos++]);
						} else {
							throw new RuntimeException();
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!rf", name = "a", descriptor = "(Lclient!ve;II)Lclient!rf;")
	public static Song create(@OriginalArg(0) Js5 archive, @OriginalArg(1) int group, @OriginalArg(2) int file) {
		@Pc(5) byte[] bytes = archive.fetchFile(group, file);
		return bytes == null ? null : new Song(new Buffer(bytes));
	}

	@OriginalMember(owner = "client!rf", name = "a", descriptor = "()V")
	public final void releasePrograms() {
		this.programs = null;
	}

	@OriginalMember(owner = "client!rf", name = "b", descriptor = "()V")
	public final void createPrograms() {
		if (this.programs != null) {
			return;
		}
		this.programs = new HashTable(16);
		@Pc(12) int[] banks = new int[16];
		@Pc(15) int[] programs = new int[16];
		banks[9] = programs[9] = 128;
		@Pc(29) MidiDecoder song = new MidiDecoder(this.midiBytes);
		@Pc(32) int trackCount = song.getTrackCount();
		@Pc(34) int track;
		for (track = 0; track < trackCount; track++) {
			song.loadTrackPosition(track);
			song.addDeltaTime(track);
			song.saveTrackPosition(track);
		}
		nextTrack:
		do {
			while (true) {
				track = song.getNextTrack();
				@Pc(56) int time = song.times[track];
				while (song.times[track] == time) {
					song.loadTrackPosition(track);
					@Pc(69) int event = song.getNextEvent(track);
					if (event == 1) {
						song.loadEndOfTrackPosition();
						song.saveTrackPosition(track);
						continue nextTrack;
					}
					@Pc(85) int eventType = event & 0xF0;
					@Pc(92) int controller;
					@Pc(98) int data1;
					@Pc(104) int data2;
					if (eventType == 176) {
						controller = event & 0xF;
						data1 = event >> 8 & 0x7F;
						data2 = event >> 16 & 0x7F;
						if (data1 == 0) {
							banks[controller] = (banks[controller] & 0xFFE03FFF) + (data2 << 14);
						}
						if (data1 == 32) {
							banks[controller] = (banks[controller] & 0xFFFFC07F) + (data2 << 7);
						}
					}
					if (eventType == 192) {
						controller = event & 0xF;
						data1 = event >> 8 & 0x7F;
						programs[controller] = banks[controller] + data1;
					}
					if (eventType == 144) {
						controller = event & 0xF;
						data1 = event >> 8 & 0x7F;
						data2 = event >> 16 & 0x7F;
						if (data2 > 0) {
							@Pc(179) int program = programs[controller];
							@Pc(187) ByteArrayNode node = (ByteArrayNode) this.programs.get(program);
							if (node == null) {
								node = new ByteArrayNode(new byte[128]);
								this.programs.put(node, program);
							}
							node.value[data1] = 1;
						}
					}
					song.addDeltaTime(track);
					song.saveTrackPosition(track);
				}
			}
		} while (!song.hasNextTrack());
	}
}
