package logika;

 //Mo�ni igralci.

public enum Igralec {
	rdeci, modri;
	
	public Igralec nasprotnik() { 
		//�e rde� potem vrne moder, sicer rdec
		return(this == rdeci ? modri: rdeci);
	}

	public Polje getPolje() {
		return (this == rdeci ? Polje.rdece : Polje.modro);
	}
	
	@Override
	public String toString() {
		return (this == rdeci ? "rdeci" : "modri");
	}
}
