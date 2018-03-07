public class Country {
    private String country;
    private long userId;
    private long count;

    public Country(String country, long userId, long count) {
        this.country = country;
        this.userId = userId;
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public long getUserId() {
        return userId;
    }

    public long getCount() {
        return count;
    }
}
