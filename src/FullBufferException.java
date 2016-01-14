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
 * Custom FullBufferException
 * @author varungoel
 *
 */
public class FullBufferException extends Exception {
	
	public FullBufferException(){
		System.out.println("ALL BUFFERS ARE FULL! PACKET DROPPED FROM NETWORK");
	}

}
