package app.model;

import java.sql.Date;

public class Expense {
	private int id;
	private Date claimedAt;
	private String claimerId;
	private Employee claimer;
	private String title;
	private String paymentDestination;
	private int amount;
	private Date updatedAt;
	private String updaterId;
	private Employee updater;
	private int status;
	private String reason;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getClaimedAt() {
		return claimedAt;
	}

	public void setClaimedAt(Date claimedAt) {
		this.claimedAt = claimedAt;
	}

	public String getClaimerId() {
		return claimerId;
	}

	public void setClaimerId(String claimerId) {
		this.claimerId = claimerId;
	}

	public Employee getClaimer() {
		return claimer;
	}

	public void setClaimer(Employee claimer) {
		this.claimer = claimer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPaymentDestination() {
		return paymentDestination;
	}

	public void setPaymentDestination(String paymentDestination) {
		this.paymentDestination = paymentDestination;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public Employee getUpdater() {
		return updater;
	}

	public void setUpdater(Employee updater) {
		this.updater = updater;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
