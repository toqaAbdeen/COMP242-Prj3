package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;

public class Date implements Comparable<Date> {

	private String date;
	private LocalDate localDate;
	private DatePicker datePicker;
	private AVLTree<Martyr> tree;

	public Date() {
	}

	public Date(LocalDate localDate) {
		this.localDate = localDate;
		this.date = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		tree = new AVLTree<Martyr>();
	}
	

	public AVLTree<Martyr> getTree() {
		return tree;
	}

	public void setTree(AVLTree<Martyr> tree) {
		this.tree = tree;
	}

	public Date(DatePicker datePicker) {
		this.datePicker = datePicker;
		this.localDate = datePicker.getValue();
		this.date = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		tree = new AVLTree<Martyr>();

	}

	public Date(String string) {
		this.date = string;
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}

	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
		this.localDate = datePicker.getValue();
		this.date = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public String getDate() {
		return date;
	}

	public void setDate(String dateString) {
		if (dateString == null || dateString.isEmpty()) {
			return;
		}
		dateString = dateString.trim().replaceAll("\\n", "");
		this.date = dateString;
		this.localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
		this.date = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (datePicker != null) {
			datePicker.setValue(localDate); // Update DatePicker if set
		}
	}

	@Override
	public int compareTo(Date o) {
		return this.date.compareTo(o.date);
	}

	@Override
	public String toString() {
		return date +" " ;
	}

	public String printDate() {
		return "" + date;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Date other = (Date) obj;
		return localDate.equals(other.localDate);
	}

	@Override
	public int hashCode() {
		return date.hashCode();
	}

}
