package model;

public class Receipt {
	
	private int receiptId;
	private int messageId;
	private int userId;
	private int isRead;
	
	public static final int UNREAD= 0;
	public static final int READ= 1;
	
	public Receipt() {
		super();
	}
	
	

	public Receipt(int receiptId,int messageId, int userId, int isRead) {
		super();
		this.receiptId = receiptId;
		this.messageId = messageId;
		this.userId = userId;
		this.isRead = isRead;
	}



	public int getReceiptId() {
		return receiptId;
	}

	public int getUserId() {
		return userId;
	}

	public int getIsRead() {
		return isRead;
	}

	public int getMessageId() {
		return messageId;
	}


	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}



	@Override
	public String toString() {
		return "Receipt [receiptId=" + receiptId + ", messageId=" + messageId
				+ ", userId=" + userId + ", isRead=" + isRead + "]";
	}

	
	
}
