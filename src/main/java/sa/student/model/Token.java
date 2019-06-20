package sa.student.model;

public class Token{
	
	public Token(int userId, String token, long expirationDate){
		this.userId = userId;
		this.token = token;
		this.expiration = expirationDate;
	}
	
	private int userId;
	private String token;
	private long expiration;
	
	public int getUserId(){
		return userId;
	}
	
	public String getToken(){
		return token;
	}
	
	public long getExpiration(){
		return expiration;
	}
}
