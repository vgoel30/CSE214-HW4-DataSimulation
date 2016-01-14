/** @author varungoel
 * Name: Varun Goel
 *ID: 109991128
 * email: varun.goel@stonybrook.edu
 * CSE 214 HW 4
 * Recitation Section: 7
 * Recitation TA: Anthony Musco
 * Grading TA: Zhichuang Sun
 */

/**
 * Packet class
 * @author varungoel
 */
public class Packet {
	
	/**
	 * Used to assign an id to a newly created packet. It will start with the value 0, and every time a new packet object is created, increment this counter and assign the value as the id of the Packet
	 */
	static int packetCount = 0;
	
	/**
	 * A unique ID for the packet that will be assigned to each packet
	 */
	int id;
	
	/**
	 * the size of the packet being sent. This value is randomly determined by the simulator by using the Math.random() method
	 */
	int packetSize;
	
	/**
	 * The time the packet arrives is stored here
	 */
	int timeArrive;
	
	/**
	 * Contains the number of simulation units that it takes for a packet to arrive at the destination router.
	 * The value will start at one hundredth of the packet size.
	 * At every simulation time unit, this counter will decrease. Once it reaches 0, we can assume that the packet has arrived at the destination.
	 */
	int timeToDest;
	
	/**
	 * Default constructor
	 */
	public Packet(){
		id = 0;
		packetSize = 0;
		timeArrive = 0;
	}
	
	/**
	 * Overloaded constructor which takes in id, packetSize and timeArrive
	 * @param id
	 * @param packetSize
	 * @param timeArrive
	 */
	public Packet(int id, int packetSize, int timeArrive){
		this.id = id;
		this.packetSize = packetSize;
		this.timeArrive = timeArrive;
		this.timeToDest = this.packetSize/100; //timeToDest == packetSize/100 (initial)
	}

	/**
	 * Accessor method for id
	 * @return id
	 */
	public int getid(){
		return this.id;
	}

	/**
	 * Mutator method for id
	 * @param id
	 */
	public void setid(int id){
		this.id = id;
	}
	
	/**
	 * Accessor method for packet size
	 * @return packetSize
	 */
	public int getpacketSize(){
		return this.packetSize;
	}

	/**
	 * Mutator method for packetSize
	 * @param size
	 */
	public void setpacketSize(int size){
		packetSize = size;
	}
	
	/**
	 * Accessor method for arrival time of the packet
	 * @return timeArrive
	 */
	public int gettimeArrive(){
		return this.timeArrive;
	}
	
	/**
	 * Mutator method for arrival time
	 * @param time
	 */
	public void settimeArrive(int time){
		timeArrive = time;
	}
	
	/**
	 * Accessor method for time to destination
	 * @return timeToDest
	 */
	public int gettimeToDest(){
		return this.timeToDest;
	}
	
	/**
	 * Overrides the to String method
	 * @return String representation
	 */
	public String toString(){
		return "[ " + this.id + ", " + this.timeArrive + ", " + this.timeToDest + " ]";
	}
	
	public static void main(String[] args) {
		
	}

}
