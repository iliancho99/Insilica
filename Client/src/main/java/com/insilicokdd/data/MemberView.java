package com.insilicokdd.data;

public class MemberView {
    private String gender;
    private String name;
    private String phone;
    private String country;
    private String city;
    private String postCode;
    private String username;
    private String password;
    private String status;

    public MemberView(String gender, String name, String phone, String country,
                      String city, String postCode, String username, String password,
                      String status) {
        this.gender = gender;
        this.name = name;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.username = username;
        this.password = password;
        this.status = status;
    }


    @Override
    public String toString() {
        return "{ \r\n" +
                "   \"gender\":\"" + gender + "\",\r\n" +
                "   \"name\":\"" + name + "\",\r\n" +
                "   \"phone\":\"" + phone + "\",\r\n" +
                "   \"address\":{ \r\n" +
                "      \"country\":\"" + country + "\",\r\n" +
                "      \"city\":\"" + city + "\",\r\n" +
                "      \"postCode\":\"" + postCode + "\"\r\n" +
                "   },\r\n" +
                "   \"account\":{ \r\n" +
                "      \"username\":\"" + username + "\",\r\n" +
                "      \"password\":\"" + password + "\",\r\n" +
                "      \"status\":\"" + status + "\"\r\n" +
                "   }\r\n" +
                "}";
    }
}