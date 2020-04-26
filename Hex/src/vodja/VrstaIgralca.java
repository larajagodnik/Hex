package vodja;

public enum VrstaIgralca {
	R, C;
	
	@Override
	public String toString() {
		switch (this) {
		case R: return "Racunalnik";
		case C: return "Clovek";
		default: assert false; return "";
		}
	}
}
