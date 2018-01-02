package cn.com.dhcc.traps.models;

import java.math.BigInteger;

public class ActiveAlarm {
	private CmoType cmoType;
	/**
	 * @return the cmoType
	 */
	public CmoType getCmoType() {
		return cmoType;
	}
	/**
	 * @param cmoType the cmoType to set
	 */
	public void setCmoType(CmoType cmoType) {
		this.cmoType = cmoType;
	}
	private String moType;
	/**
	 * @return the moType
	 */
	public String getMoType() {
		return moType;
	}
	private String topMoType;
	
	/**
	 * @return the topMoType
	 */
	public String getTopMoType() {
		
		return topMoType;
	}
	/**
	 * @param topMoType the topMoType to set
	 */
	public void setTopMoType(String topMoType) {
		this.topMoType = topMoType;
		
	}
	/**
	 * @param moType the moType to set
	 */
	public void setMoType(String moType) {
		this.moType = moType;
		this.topMoType = moType.substring(0, moType.length()>=4?4:moType.length());
	}
	private BigInteger almSn;
	/**
	 * @return the almSn
	 */
	public BigInteger getAlmSn() {
		return almSn;
	}
	/**
	 * @param almSn the almSn to set
	 */
	public void setAlmSn(BigInteger almSn) {
		this.almSn = almSn;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ActiveAlarm [cmoType=" + cmoType + ", moType=" + moType
				+ ", topMoType=" + topMoType + ", almSn=" + almSn + ", cause="
				+ cause + ", severity=" + severity + ", detail=" + detail
				+ ", moIp=" + moIp + ", flag=" + flag + "]";
	}
	private String cause;
	private int severity;
	private String detail;
	private String moIp;
	/**
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}
	/**
	 * @param cause the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}
	/**
	 * @return the severity
	 */
	public int getSeverity() {
		return severity;
	}
	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * @return the moIp
	 */
	public String getMoIp() {
		return moIp;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((almSn == null) ? 0 : almSn.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActiveAlarm other = (ActiveAlarm) obj;
		if (almSn == null) {
			if (other.almSn != null)
				return false;
		} else if (!almSn.equals(other.almSn))
			return false;
		return true;
	}
	/**
	 * @param moIp the moIp to set
	 */
	public void setMoIp(String moIp) {
		this.moIp = moIp;
	}
	
	private int flag;
	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

}
