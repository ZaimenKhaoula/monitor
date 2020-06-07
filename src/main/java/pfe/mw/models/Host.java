package pfe.mw.models;

import org.springframework.data.annotation.Id;   
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="hosts")
public class Host {
	
	@Id 
	private String ipAddress;              //the ip address of the host where the HEM is running
	private String portNumber;             //the port on which the HEM is running
	private String hostName;               //the name of the host where the HEM is running 
	
	//RAM
	private double ramSize;                //the total amount of ram of the host in MegaBytes
	private double ramAvailable;           //the size of ram not used in the host in MegaBytes
	
	//ROM
	private double diskSize;             //the total disque space of the host in MegaBytes
	private double diskSpaceAvailable;   //the total free disque space of the host in MegaBytes 
	
	//CPU
	private int    nbLogicalProcessors;    //the number of logical processors
	private double amountUsedCPU;          //the amount of used CPU from the total CPU capacity in %
	
	//OS
	private String osName; 				   // operating system name
	private String osType;                 // operating system type
	private String osVersion;              // operating system version
	
	
	
	//getters and setters
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public double getRamSize() {
		return ramSize;
	}
	public void setRamSize(double ramSize) {
		this.ramSize = ramSize;
	}
	public double getRamAvailable() {
		return ramAvailable;
	}
	public void setRamAvailable(double ramAvailable) {
		this.ramAvailable = ramAvailable;
	}
	public double getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(double diskSize) {
		this.diskSize = diskSize;
	}
	public double getDiskSpaceAvailable() {
		return diskSpaceAvailable;
	}
	public void setDiskSpaceAvailable(double diskSpaceAvailable) {
		this.diskSpaceAvailable = diskSpaceAvailable;
	}
	public int getNbLogicalProcessors() {
		return nbLogicalProcessors;
	}
	public void setNbLogicalProcessors(int nbLogicalProcessors) {
		this.nbLogicalProcessors = nbLogicalProcessors;
	}
	public double getAmountUsedCPU() {
		return amountUsedCPU;
	}
	public void setAmountUsedCPU(double amountUsedCPU) {
		this.amountUsedCPU = amountUsedCPU;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
}