package com.lt.constants;

/**
 * 
 * @author JEDI-03
 * Enumeration class for Mode of Payments
 *
 */
public enum ModeOfPayment {
	
	CREDIT_CARD,NET_BANKING,DEBIT_CARD,CASH,CHEQUE;
	
	/**
	 * Method to get Mode of Payment
	 * @param value
	 * @return Mode of Payment
	 */
	public static ModeOfPayment getModeofPayment(int value)
	{
		switch(value)
		{
			case 1:
				return ModeOfPayment.CREDIT_CARD;
			case 2:
				return ModeOfPayment.NET_BANKING;
			case 3:
				return ModeOfPayment.DEBIT_CARD;
			case 4:
				return ModeOfPayment.CASH;
			case 5:
				return ModeOfPayment.CHEQUE;
			default:
				return null;
				
		}
			
	}
	
}
	
