package nl.mightydev.version;

import nl.mightydev.lumberjack.util.Number;

public class LumberjackVersion {
	public final MinecraftVersion minecraft;
	public final int revision;
	
	public LumberjackVersion(String version) {
		String parts[] = version.split("v");
		boolean valid = parts.length == 2;
		minecraft = new MinecraftVersion(valid ? parts[0] : ""); 
		revision = valid ? Number.parse(parts[1], -1) : -1;
	}
	
	public boolean equals(Object other) {
		return other instanceof LumberjackVersion && equals((LumberjackVersion)other);
	}
	
	public boolean equals(LumberjackVersion other) {
		return revision == other.revision;
	}

	public String toString() {
		return minecraft + "v" + (revision != -1 ? revision : "invalid Lumberjack version");
	}
}
