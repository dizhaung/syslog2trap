package cn.com.dhcc.traps.models;

import java.math.BigInteger;

public class CmoType {
	public String moType;
	public String alias;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CmoType [moType=" + moType + ", alias=" + alias + "]";
	}
	/**
	 * @return the moType
	 */
	public String getMoType() {
		return moType;
	}
	/**
	 * @param moType the moType to set
	 */
	public void setMoType(String moType) {
		this.moType = moType;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
