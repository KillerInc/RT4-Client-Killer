package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!ii")
public final class Js5Index {

	@OriginalMember(owner = "client!ii", name = "b", descriptor = "[[I")
	public int[][] fileNameHashes;

	@OriginalMember(owner = "client!ii", name = "d", descriptor = "[I")
	public int[] groupChecksums;

	@OriginalMember(owner = "client!ii", name = "f", descriptor = "I")
	public int size;

	@OriginalMember(owner = "client!ii", name = "h", descriptor = "Lclient!jg;")
	public IntHashTable groupNameHashTable;

	@OriginalMember(owner = "client!ii", name = "m", descriptor = "[I")
	public int[] groupIds;

	@OriginalMember(owner = "client!ii", name = "n", descriptor = "[I")
	public int[] groupCapacities;

	@OriginalMember(owner = "client!ii", name = "o", descriptor = "[I")
	public int[] groupNameHashes;

	@OriginalMember(owner = "client!ii", name = "p", descriptor = "[I")
	public int[] groupSizes;

	@OriginalMember(owner = "client!ii", name = "r", descriptor = "[I")
	public int[] groupVersions;

	@OriginalMember(owner = "client!ii", name = "s", descriptor = "[[I")
	public int[][] fileIds;

	@OriginalMember(owner = "client!ii", name = "u", descriptor = "I")
	public int capacity;

	@OriginalMember(owner = "client!ii", name = "v", descriptor = "I")
	public int version;

	@OriginalMember(owner = "client!ii", name = "x", descriptor = "[Lclient!jg;")
	public IntHashTable[] fileNameHashTables;

	@OriginalMember(owner = "client!ii", name = "z", descriptor = "I")
	public final int checksum;

	@OriginalMember(owner = "client!ii", name = "<init>", descriptor = "([BI)V")
	public Js5Index(@OriginalArg(0) byte[] data, @OriginalArg(1) int expectedChecksum) {
		this.checksum = Buffer.crc32(data, data.length);
		if (expectedChecksum != this.checksum) {
			throw new RuntimeException();
		}
		this.decode(data);
	}

	@OriginalMember(owner = "client!ii", name = "a", descriptor = "(I[B)V")
	private void decode(@OriginalArg(1) byte[] data) {
		@Pc(12) Buffer buffer = new Buffer(Js5Compression.uncompress(data));
		@Pc(16) int formatVersion = buffer.g1();
		if (formatVersion != 5 && formatVersion != 6) {
			throw new RuntimeException();
		}
		if (formatVersion >= 6) {
			this.version = buffer.g4();
		} else {
			this.version = 0;
		}
		@Pc(48) int flags = buffer.g1();
		@Pc(50) int delta = 0;
		this.size = buffer.g2();
		@Pc(59) int maxGroupId = -1;
		this.groupIds = new int[this.size];
		@Pc(66) int i;
		for (i = 0; i < this.size; i++) {
			this.groupIds[i] = delta += buffer.g2();
			if (this.groupIds[i] > maxGroupId) {
				maxGroupId = this.groupIds[i];
			}
		}
		this.capacity = maxGroupId + 1;
		this.groupVersions = new int[this.capacity];
		this.fileIds = new int[this.capacity][];
		this.groupChecksums = new int[this.capacity];
		this.groupCapacities = new int[this.capacity];
		this.groupSizes = new int[this.capacity];
		if (flags != 0) {
			this.groupNameHashes = new int[this.capacity];
			for (i = 0; i < this.capacity; i++) {
				this.groupNameHashes[i] = -1;
			}
			for (i = 0; i < this.size; i++) {
				this.groupNameHashes[this.groupIds[i]] = buffer.g4();
			}
			this.groupNameHashTable = new IntHashTable(this.groupNameHashes);
		}
		for (i = 0; i < this.size; i++) {
			this.groupChecksums[this.groupIds[i]] = buffer.g4();
		}
		for (i = 0; i < this.size; i++) {
			this.groupVersions[this.groupIds[i]] = buffer.g4();
		}
		for (i = 0; i < this.size; i++) {
			this.groupSizes[this.groupIds[i]] = buffer.g2();
		}
		@Pc(273) int groupId;
		@Pc(278) int groupSize;
		@Pc(280) int maxFileId;
		@Pc(288) int fileId;
		for (i = 0; i < this.size; i++) {
			delta = 0;
			groupId = this.groupIds[i];
			groupSize = this.groupSizes[groupId];
			maxFileId = -1;
			this.fileIds[groupId] = new int[groupSize];
			for (fileId = 0; fileId < groupSize; fileId++) {
				@Pc(306) int id = this.fileIds[groupId][fileId] = delta += buffer.g2();
				if (id > maxFileId) {
					maxFileId = id;
				}
			}
			this.groupCapacities[groupId] = maxFileId + 1;
			if (maxFileId + 1 == groupSize) {
				this.fileIds[groupId] = null;
			}
		}
		if (flags == 0) {
			return;
		}
		this.fileNameHashTables = new IntHashTable[maxGroupId + 1];
		this.fileNameHashes = new int[maxGroupId + 1][];
		for (i = 0; i < this.size; i++) {
			groupId = this.groupIds[i];
			groupSize = this.groupSizes[groupId];
			this.fileNameHashes[groupId] = new int[this.groupCapacities[groupId]];
			for (maxFileId = 0; maxFileId < this.groupCapacities[groupId]; maxFileId++) {
				this.fileNameHashes[groupId][maxFileId] = -1;
			}
			for (maxFileId = 0; maxFileId < groupSize; maxFileId++) {
				if (this.fileIds[groupId] == null) {
					fileId = maxFileId;
				} else {
					fileId = this.fileIds[groupId][maxFileId];
				}
				this.fileNameHashes[groupId][fileId] = buffer.g4();
			}
			this.fileNameHashTables[groupId] = new IntHashTable(this.fileNameHashes[groupId]);
		}
	}
}
