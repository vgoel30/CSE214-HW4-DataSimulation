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
 * Simulator class 
 * @author varungoel
 */
public class Simulator {

	/**
	 * Dispatcher router 
	 */
	static Router dispatcher = new Router();

	/**
	 * Destination router
	 */
	static Router destination = new Router();

	/**
	 * Array that has the intermediate routers
	 */
	static Router[] routers ;

	/**
	 * Method to return a random integer between the parameters; both inclusive
	 * @param minVal
	 * @param maxVal
	 * @return random integer in specified range
	 */
	private static int randInt(int minVal, int maxVal){
		return (int)(minVal + (maxVal-minVal+1)*Math.random());
	}

	/**
	 * Simulation method that is actually carried out.
	 * @param duration: duration of the simulation
	 * @param minVal: minimum size of the packet
	 * @param maxVal: maximum size of the packet
	 * @param maxSize: maximum size of the buffer of a router
	 * @param number:  number of intermediate routers
	 * @param bandwidth: bandwidth of the destination router
	 * @param arrivalProb: probability of packet arrival
	 * @throws Exception
	 */
	public double simulate(int duration, int minVal, int maxVal, int maxSize, int number, int bandwidth, double arrivalProb) throws Exception{
		int id = 0;

		// Contains the running sum of the total time each packet is in the network 
		int totalServiceTime = 0;

		//Contains the total number of packets that has been successfully forwarded to the destination
		int totalPacketsArrived = 0;

		//average service time
		double averageTime = 0;

		//records the total number of packets that have been dropped
		int packetsDropped = 0;

		//array list that will be used to keep track of the packets that are ready to be sent to the final destination. This will be used to ensure fairness
		ArrayList<Integer> waitingList = new ArrayList<Integer>();

		//start the simulation
		for(int seconds = 1; seconds <= duration; seconds ++){
			System.out.println("TIME UNIT: " + seconds);
			//for checking if a packet has arrived or not
			for(int i = 1; i <=3; i++){
				if(Math.random() < arrivalProb){
					id++;
					int size = randInt(minVal,maxVal);
					dispatcher.enqueue(new Packet(id,size,seconds)); //change the 1 to seconds when using the final simulation loop
					System.out.println("Packet number " + id +" " + " arrives at dispatcher with size " + size );
					//System.out.print();

					try{
						System.out.println("Packet number " + id +" " + " sent to router number " + Router.sendPacketTo(routers,maxSize) + "\n");
						routers[(Router.sendPacketTo(routers,maxSize))].enqueue(dispatcher.get(0));
						try{
							dispatcher.dequeue();
						}
						catch(Exception e){

						}
					}
					catch(FullBufferException e){
						packetsDropped++;
						System.out.println("Total packets dropped: "+ packetsDropped + "\n");
					}
				}
			}

			//after each time unit, decrement the timeToDest for the packet at the front of each router queue
			for(int a = 1; a <= number; a++){
				//decrement timeToDest counter
				if(routers[a].size() > 0){
					//if the timeToDest of the packet has reached 0, put it on the waiting list to be sent to the destination

					if(routers[a].get(0).timeToDest == 0){
						//this makes sure that in case the packet wasn't sent in the previous time unit, it doesn't get added to the waiting list again
						if(!waitingList.contains(a)){
							waitingList.add(a);
						}
					}

					//decrement timeToDest
					else{
						--routers[a].get(0).timeToDest;
					}
				}
			}

			//this will remove stuff off the waiting list and send the packets to the destination
			if(waitingList.size() != 0){

				if(waitingList.size() > bandwidth){
					System.out.println("We can send only " + (waitingList.size() - bandwidth) + " packets to destination right now due to limited bandwidth ");
					System.out.println((waitingList.size() - bandwidth) + " packets are still on the waiting list");
				}

				for(int c = 0; c< Math.min(bandwidth, waitingList.size()); c++){
					if(routers[waitingList.get(c)].size()!=0){
						try{
							totalServiceTime = totalServiceTime + seconds - routers[waitingList.get(c)].peek().gettimeArrive();
							System.out.println("Packet " + routers[waitingList.get(c)].peek() + " with ID number " + routers[waitingList.get(c)].peek().getid()  + " SENT TO DESTINATION and it was in the network for " + (seconds - routers[waitingList.get(c)].peek().gettimeArrive()) + " seconds");
						}catch(Exception e){

						}
						try{
							destination.add(routers[waitingList.get(c)].dequeue()); //adds to the destination 
						}catch(Exception e){	
						}

						waitingList.remove(0); //removes the index from the waiting list
						totalPacketsArrived++;
					}
				}
			}

			for(int i = 1; i < routers.length; i++){
				System.out.println("Router" + i + ": " + routers[i]);
			}
			System.out.println();
		}

		System.out.println("\n\n\nEND OF SIMULATION\nSTATISTICS AND RELATED INFORMATION:\n");
		System.out.println("Destination router: " + destination);
		System.out.println("Total packets dropped: "+ packetsDropped + "\n");
		System.out.println("PACKETS SERVICED: " + totalPacketsArrived);
		System.out.println("TOTAL SERVICE TIME: "+ totalServiceTime + "\n");

		if(totalPacketsArrived == 0){
			System.out.print("AVERAGE SERVICE TIME: " + 0);
			return averageTime;
		}

		else{
			System.out.print("AVERAGE SERVICE TIME: " + (double)totalServiceTime/(double)totalPacketsArrived + "\n\n");
			return (double)totalServiceTime/(double)totalPacketsArrived;
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		String prompt;
		Simulator S1 = new Simulator(); // need to create an instance to call the simulate method

		do{
			//number of intermediate routers
			int number ;
			do{
				System.out.println("How many intermediate routers do you need? (Enter a positive number greater than 0)");
				number = s.nextInt();
			}while(number <= 0);

			//creates the array for intermediate routers
			routers = new Router[number + 1];
			//the element at 0th index is null
			routers[0] = null;

			for(int i = 1; i <= number; i++){
				routers[i] = new Router();
			}

			double arrivalProb = 0;
			do{
				System.out.println("What is the probability of a packet arrival?");
				arrivalProb = s.nextDouble();
			}while(arrivalProb > 1 || arrivalProb < 0);

			//buffer size of the router
			int maxSize;
			do{
				System.out.println("What is the maximum number of packets that a router can hold?");
				maxSize = s.nextInt();
			}while(maxSize <= 0);


			//minimum packet size
			int minVal;
			do{
				System.out.println("Minimum Packet Size?");
				minVal = s.nextInt();
			}while(minVal < 0);

			//maximum packet size
			int maxVal;
			do{
				System.out.println("Maximum Packet Size?");
				maxVal = s.nextInt();
			}while(maxVal <= 0);

			//bandwidth
			int bandwidth;
			do{
				System.out.println("Enter bandwidth size for the destination: ");
				bandwidth = s.nextInt();
			}while(bandwidth <= 0);

			//duration
			int duration;
			do{
				System.out.println("Enter total simulation duration: ");
				duration = s.nextInt();
			}while(duration <= 0);

			//calls the simulate method and prints out the average time and the whole sequence of time in which the simulation took place
			S1.simulate(duration,minVal,maxVal,maxSize,number,bandwidth,arrivalProb);

			s.nextLine(); //clears the buffer to ask for next input
			System.out.println("Enter Y/y if you want another simulation");
			prompt = s.nextLine();

		}while(prompt.toUpperCase().equals("Y"));
		s.close();
	}
}