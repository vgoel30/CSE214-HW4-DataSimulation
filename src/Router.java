import java.util.*;

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
 * Router class
 * @author varungoel
 */
public class Router extends ArrayList<Packet> {

	/**
	 * Enqueue method to append a packet at the end of the router buffer
	 * @param p
	 */
	public void enqueue(Packet p){
		add(p);
	}

	/**
	 * Dequeue method to remove the first packet in the router buffer
	 * @return packet at the front
	 * @throws Exception 
	 */
	public Packet dequeue() throws Exception{
		if(isEmpty()){
			throw new Exception();
		}

		return remove(0);
	}

	/**
	 * Peek method to just see the packet at the top of the buffer
	 * @return Packet at the start
	 * @throws Exception 
	 */
	public Packet peek() throws Exception{
		if(isEmpty()){
			throw new Exception();
		}
		
		return get(0);
	}

	/**
	 * Method to find size of router
	 * @return integer size
	 */
	public int Size(){
		return size();
	}
	
	/**
	 * Method to check if router is empty
	 * @return boolean telling whether empty or not
	 */
	public boolean isEmpty(){
		return Size()==0;
	}

	/**
	 * Overrides the toString method to print the buffer in the desired format
	 * @return String representation of the buffer
	 */
	public String toString(){
		if(size() == 0)
			return "{}";

		String toReturn = "{ ";

		for(int i = 0; i < size()-1; i++){
			toReturn = toReturn + get(i).toString() + ", " ;
		}

		toReturn = toReturn + get(size()-1).toString() + " }";

		return toReturn;
	}


	/**
	 * sendPacketTo method. The helper method is more powerful and will be used in the final simulator.
	 * @param routers
	 * @return integer index of router which can accommodate the packet
	 */
	public static int sendPacketTo(Router[] routers){

		int len = routers.length;
		int mostEmpty = 1; //hold the index of the router with the most free space

		//if the array holding all the routers is empty
		if(len == 1)
			return -1;

		for(int i = 1; i < len; i++){
			if(routers[i].size() < routers[mostEmpty].size())
				mostEmpty = i;
		}

		return mostEmpty;
	}

	//overloaded sendPacketTo method with additional  to check for full buffers

	/**
	 * Helper method for sendPacketTo
	 * @param Array of routers
	 * @param maxSize: the maximum number of packets that a router can hold
	 * @return Index of the router to which to send the packet to
	 * @throws FullBufferException
	 */
	public static int sendPacketTo(Router[] array, int maxSize) throws FullBufferException{

		int len = array.length;
		int mostEmpty = 1; //hold the index of the router with the most free space
		int fullRouters = 0; //number of routers that are full

		//if the array holding all the routers is empty
		if(len == 1)
			return -1;

		for(int i = 1; i < len; i++){


			if(array[i].size() == maxSize){
				fullRouters = fullRouters + 1;

			}
			//if the number of full routers is equal to the number of routers in the router queue, throw full buffer exception
			if(fullRouters == (len-1))
				throw new FullBufferException();

			if(array[i].size() < array[mostEmpty].size())
				mostEmpty = i; 
		}

		return mostEmpty;
	}


	public static void main (String[] args){

	}
}
