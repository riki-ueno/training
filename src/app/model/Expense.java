package app.model;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

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

	static public Expense build(HttpServletRequest request) {
		Expense expense = new Expense();

		String id = request.getParameter("id");
		String claimedAt = request.getParameter("claimedAt");
		String claimerId = request.getParameter("claimerId");
		String title = request.getParameter("title");
		String paymentDestination = request.getParameter("paymentDestination");
		String amount = request.getParameter("amount");
		String updatedAt = request.getParameter("updatedAt");
		String updaterId = request.getParameter("updaterId");
		String status = request.getParameter("status");
		String reason = request.getParameter("reason");

		if (NumberUtils.isDigits(id)) expense.setId(Integer.parseInt(id));
		if (claimedAt != null && !claimedAt.isEmpty()) expense.setClaimedAt(Date.valueOf(claimedAt));
		expense.setClaimerId(claimerId);
		expense.setTitle(title);
		expense.setPaymentDestination(paymentDestination);
		if (NumberUtils.isDigits(amount)) expense.setAmount(Integer.parseInt(amount));
		if (updatedAt != null && !updatedAt.isEmpty()) expense.setUpdatedAt(Date.valueOf(updatedAt));
		expense.setUpdaterId(updaterId);
		if (NumberUtils.isDigits(status)) expense.setStatus(Integer.parseInt(status));
		expense.setReason(reason);

		return expense;
	}
}
