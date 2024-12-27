package application;

public class Martyr implements Comparable<Martyr> {

	private String name;
	private String date;
	private int age;
	private String location;
	private String district;
	private char gender;

	public Martyr() {
	}

	public Martyr(String name) {
		super();
		this.name = name;
	}

	public Martyr(String name, String date, int age, String location, String district, char gender) {
		super();
		setName(name);
		setAge(age);
		setDate(date);
		setDistrict(district);
		setLocation(location);
		setGender(gender);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age >= 0)
			this.age = age;
		else
			System.out.println(getName() + " Age must be >= 0");
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		if (Character.toUpperCase(gender) == 'M' || Character.toUpperCase(gender) == 'F')
			this.gender = gender;
		else
			System.out.println(getName() + " Gender must be F/M");
	}

	@Override
	public int hashCode() {
		return date.hashCode();

	}

	@Override
	public int compareTo(Martyr o) {
		if (o == null || o.district == null)
			return -1;

		if (this.district.compareToIgnoreCase(o.district) != 0)
			return (this.district.compareToIgnoreCase(o.district));
		return (this.name.compareToIgnoreCase(o.name));

	}

	@Override
	public String toString() {
		return "" + name + ", " + date + "," + age + "," + location + ", " + district + ", " + gender + "\n";
	}

}
