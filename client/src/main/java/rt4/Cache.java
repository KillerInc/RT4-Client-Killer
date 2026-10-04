package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.io.EOFException;
import java.io.IOException;

@OriginalClass("client!ge")
public final class Cache {

	@OriginalMember(owner = "client!wc", name = "i", descriptor = "[B")
	public static final byte[] buffer = new byte[520];

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "Lclient!en;")
	private BufferedFile data = null;

	@OriginalMember(owner = "client!ge", name = "f", descriptor = "Lclient!en;")
	private BufferedFile index = null;

	@OriginalMember(owner = "client!ge", name = "l", descriptor = "I")
	private int maxLen = 65000;

	@OriginalMember(owner = "client!ge", name = "c", descriptor = "I")
	private final int archive;

	@OriginalMember(owner = "client!ge", name = "<init>", descriptor = "(ILclient!en;Lclient!en;I)V")
	public Cache(@OriginalArg(0) int archive, @OriginalArg(1) BufferedFile data, @OriginalArg(2) BufferedFile index, @OriginalArg(3) int maxLen) {
		this.maxLen = maxLen;
		this.index = index;
		this.archive = archive;
		this.data = data;
	}

	@OriginalMember(owner = "client!ge", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public final String toString() {
		return "Cache:" + this.archive;
	}

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "(II[BB)Z")
	public final boolean write(@OriginalArg(0) int group, @OriginalArg(1) int len, @OriginalArg(2) byte[] src) {
		@Pc(7) BufferedFile lock = this.data;
		synchronized (this.data) {
			if (len < 0 || len > this.maxLen) {
				throw new IllegalArgumentException();
			}
			@Pc(35) boolean success = this.write(len, group, src, true);
			if (!success) {
				success = this.write(len, group, src, false);
			}
			return success;
		}
	}

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "(IB)[B")
	public final byte[] read(@OriginalArg(0) int group) {
		@Pc(9) BufferedFile lock = this.data;
		synchronized (this.data) {
			try {
				if (this.index.length() < (long) (group * 6 + 6)) {
					return null;
				}
				this.index.seek(group * 6);
				this.index.read(0, buffer, 6);
				@Pc(69) int sector = ((buffer[3] & 0xFF) << 16) - (-((buffer[4] & 0xFF) << 8) - (buffer[5] & 0xFF));
				@Pc(99) int len = (buffer[2] & 0xFF) + ((buffer[1] & 0xFF) << 8) + ((buffer[0] & 0xFF) << 16);
				if (len < 0 || this.maxLen < len) {
					return null;
				} else if (sector <= 0 || (long) sector > this.data.length() / 520L) {
					return null;
				} else {
					@Pc(134) byte[] result = new byte[len];
					@Pc(136) int bytesRead = 0;
					@Pc(138) int chunk = 0;
					while (bytesRead < len) {
						if (sector == 0) {
							return null;
						}
						@Pc(157) int chunkLen = len - bytesRead;
						this.data.seek(sector * 520);
						if (chunkLen > 512) {
							chunkLen = 512;
						}
						this.data.read(0, buffer, chunkLen + 8);
						@Pc(197) int headerGroup = ((buffer[0] & 0xFF) << 8) + (buffer[1] & 0xFF);
						@Pc(211) int headerChunk = (buffer[3] & 0xFF) + ((buffer[2] & 0xFF) << 8);
						@Pc(217) int headerArchive = buffer[7] & 0xFF;
						@Pc(239) int nextSector = (buffer[6] & 0xFF) + ((buffer[5] & 0xFF) << 8) + ((buffer[4] & 0xFF) << 16);
						if (group != headerGroup || chunk != headerChunk || this.archive != headerArchive) {
							return null;
						}
						if (nextSector < 0 || (long) nextSector > this.data.length() / 520L) {
							return null;
						}
						for (@Pc(272) int j = 0; j < chunkLen; j++) {
							result[bytesRead++] = buffer[j + 8];
						}
						chunk++;
						sector = nextSector;
					}
					return result;
				}
			} catch (@Pc(301) IOException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "(BII[BZ)Z")
	private boolean write(@OriginalArg(1) int len, @OriginalArg(2) int group, @OriginalArg(3) byte[] src, @OriginalArg(4) boolean overwrite) {
		@Pc(9) BufferedFile lock = this.data;
		synchronized (this.data) {
			try {
				@Pc(67) int sector;
				if (overwrite) {
					if (this.index.length() < (long) (group * 6 + 6)) {
						return false;
					}
					this.index.seek(group * 6);
					this.index.read(0, buffer, 6);
					sector = ((buffer[3] & 0xFF) << 16) + (buffer[4] << 8 & 0xFF00) + (buffer[5] & 0xFF);
					if (sector <= 0 || this.data.length() / 520L < (long) sector) {
						return false;
					}
				} else {
					sector = (int) ((this.data.length() + 519L) / 520L);
					if (sector == 0) {
						sector = 1;
					}
				}
				buffer[0] = (byte) (len >> 16);
				buffer[4] = (byte) (sector >> 8);
				@Pc(125) int bytesWritten = 0;
				buffer[5] = (byte) sector;
				buffer[2] = (byte) len;
				buffer[3] = (byte) (sector >> 16);
				@Pc(156) int chunk = 0;
				buffer[1] = (byte) (len >> 8);
				this.index.seek(group * 6);
				this.index.write(buffer, 0, 6);
				while (true) {
					if (bytesWritten < len) {
						readChunk:
						{
							@Pc(189) int nextSector = 0;
							@Pc(248) int chunkLen;
							if (overwrite) {
								this.data.seek(sector * 520);
								try {
									this.data.read(0, buffer, 8);
								} catch (@Pc(209) EOFException eof) {
									break readChunk;
								}
								nextSector = ((buffer[4] & 0xFF) << 16) + ((buffer[5] & 0xFF) << 8) + (buffer[6] & 0xFF);
								chunkLen = (buffer[1] & 0xFF) + ((buffer[0] & 0xFF) << 8);
								@Pc(254) int headerArchive = buffer[7] & 0xFF;
								@Pc(268) int headerChunk = (buffer[3] & 0xFF) + ((buffer[2] & 0xFF) << 8);
								if (chunkLen != group || chunk != headerChunk || this.archive != headerArchive) {
									return false;
								}
								if (nextSector < 0 || (long) nextSector > this.data.length() / 520L) {
									return false;
								}
							}
							chunkLen = len - bytesWritten;
							if (nextSector == 0) {
								overwrite = false;
								nextSector = (int) ((this.data.length() + 519L) / 520L);
								if (nextSector == 0) {
									nextSector++;
								}
								if (nextSector == sector) {
									nextSector++;
								}
							}
							buffer[7] = (byte) this.archive;
							buffer[0] = (byte) (group >> 8);
							if (len - bytesWritten <= 512) {
								nextSector = 0;
							}
							buffer[4] = (byte) (nextSector >> 16);
							if (chunkLen > 512) {
								chunkLen = 512;
							}
							buffer[1] = (byte) group;
							buffer[6] = (byte) nextSector;
							buffer[2] = (byte) (chunk >> 8);
							buffer[3] = (byte) chunk;
							chunk++;
							buffer[5] = (byte) (nextSector >> 8);
							this.data.seek(sector * 520);
							sector = nextSector;
							this.data.write(buffer, 0, 8);
							this.data.write(src, bytesWritten, chunkLen);
							bytesWritten += chunkLen;
							continue;
						}
					}
					return true;
				}
			} catch (@Pc(453) IOException ex) {
				return false;
			}
		}
	}
}
