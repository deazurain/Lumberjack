package nl.mightydev.version;

import nl.mightydev.lumberjack.util.Number;

public class MinecraftVersion {
	public final int major;
	public final int minor;
	public final int revision;
	
	public MinecraftVersion(String version) {
		String[] parts = version.split("\\.");
		int length = parts.length;
		if(length > 0 && length <= 3) {
			major = length >= 1 ? Number.parse(parts[0], -1) : -1;
			minor = length >= 2 ? Number.parse(parts[1], -1) : -1;
			revision = length >= 3 ? Number.parse(parts[2], -1) : -1;
		}
		else {
			major = -1;
			minor = -1;
			revision = -1;
		}
	}
	
	public boolean equals(Object other) {
		return other instanceof MinecraftVersion && equals((MinecraftVersion)other);
	}
	
	public boolean equals(MinecraftVersion other) {
		return  major == other.major &&
				minor == other.minor &&
				revision == other.revision;
	}
	
	public String toString() {
		if(major == -1) {
			return "invalid Minecraft version";
		}
		if(minor == -1) {
			return "" + major;
		}
		if(revision == -1) {
			return major + "." + minor;
		}
		return major + "." + minor + "." + revision;
	}
}
