package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.SourceDataLine;
import java.awt.Component;

@OriginalClass("client!qa")
public final class JavaAudioChannel extends AudioChannel {

	@OriginalMember(owner = "client!qa", name = "L", descriptor = "I")
	private int bufferSize;

	@OriginalMember(owner = "client!qa", name = "M", descriptor = "Ljavax/sound/sampled/SourceDataLine;")
	private SourceDataLine dataLine;

	@OriginalMember(owner = "client!qa", name = "O", descriptor = "Ljavax/sound/sampled/AudioFormat;")
	private AudioFormat audioFormat;

	@OriginalMember(owner = "client!qa", name = "P", descriptor = "[B")
	private byte[] outputBuffer;

	@OriginalMember(owner = "client!qa", name = "N", descriptor = "Z")
	private boolean isSoundMax = false;

	@OriginalMember(owner = "client!qa", name = "d", descriptor = "()V")
	@Override
	protected final void flush() {
		if (this.dataLine != null) {
			this.dataLine.close();
			this.dataLine = null;
		}
	}

	@OriginalMember(owner = "client!qa", name = "a", descriptor = "(Ljava/awt/Component;)V")
	@Override
	public final void init(@OriginalArg(0) Component parent) {
		@Pc(1) Info[] mixerInfos = AudioSystem.getMixerInfo();
		if (mixerInfos != null) {
			for (@Pc(9) int i = 0; i < mixerInfos.length; i++) {
				@Pc(21) Info info = mixerInfos[i];
				if (info != null) {
					@Pc(28) String name = info.getName();
					if (name != null && name.toLowerCase().indexOf("soundmax") >= 0) {
						this.isSoundMax = true;
					}
				}
			}
		}
		this.audioFormat = new AudioFormat((float) AudioChannel.sampleRate, 16, AudioChannel.stereo ? 2 : 1, true, false);
		this.outputBuffer = new byte[0x100 << (AudioChannel.stereo ? 2 : 1)];
	}

	@OriginalMember(owner = "client!qa", name = "a", descriptor = "(I)V")
	@Override
	public final void open(@OriginalArg(0) int size) throws LineUnavailableException {
		try {
			@Pc(20) javax.sound.sampled.DataLine.Info lineInfo = new javax.sound.sampled.DataLine.Info(SourceDataLine.class, this.audioFormat, size << (AudioChannel.stereo ? 2 : 1));
			this.dataLine = (SourceDataLine) AudioSystem.getLine(lineInfo);
			this.dataLine.open();
			this.dataLine.start();
			this.bufferSize = size;
		} catch (@Pc(36) LineUnavailableException ex) {
			if (IntUtils.bitCountFast(size) == 1) {
				this.dataLine = null;
				throw ex;
			} else {
				this.open(IntUtils.clp2(size));
			}
		}
	}

	@OriginalMember(owner = "client!qa", name = "b", descriptor = "()V")
	@Override
	protected final void close() throws LineUnavailableException {
		this.dataLine.flush();
		if (!this.isSoundMax) {
			return;
		}
		this.dataLine.close();
		this.dataLine = null;
		@Pc(34) javax.sound.sampled.DataLine.Info lineInfo = new javax.sound.sampled.DataLine.Info(SourceDataLine.class, this.audioFormat, this.bufferSize << (AudioChannel.stereo ? 2 : 1));
		this.dataLine = (SourceDataLine) AudioSystem.getLine(lineInfo);
		this.dataLine.open();
		this.dataLine.start();
	}

	@OriginalMember(owner = "client!qa", name = "c", descriptor = "()I")
	@Override
	protected final int getBufferSize() {
		return this.bufferSize - (this.dataLine.available() >> (AudioChannel.stereo ? 2 : 1));
	}

	@OriginalMember(owner = "client!qa", name = "a", descriptor = "()V")
	@Override
	protected final void write() {
		@Pc(1) short sampleCount = 256;
		if (AudioChannel.stereo) {
			sampleCount = 512;
		}
		for (@Pc(9) int i = 0; i < sampleCount; i++) {
			@Pc(17) int sample = this.samples[i];
			if ((sample + 8388608 & 0xFF000000) != 0) {
				sample = sample >> 31 ^ 0x7FFFFF;
			}
			this.outputBuffer[i * 2] = (byte) (sample >> 8);
			this.outputBuffer[i * 2 + 1] = (byte) (sample >> 16);
		}
		this.dataLine.write(this.outputBuffer, 0, sampleCount << 1);
	}
}
