package com.uta.sp.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.uta.sp.dto.Student;


class JdbcConnectionTest {

	private final static Logger LOG = Logger.getLogger(JdbcConnectionTest.class);
	private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

	@Test
	void psidTest() {
		List<Map<String,Object>> studentAndGrade = new ProfessorDao().getStudentAndGrade(1,1);
		assertTrue(studentAndGrade.size()>0);
	}
	
	@Test
	void testUpdate() {
		int i= new UserDao().updateGrade(1,1,"K");
		assertTrue(i>0);

	}
	
	@Test
	void studentGetOneTest() {
		Student s=new Student();
		s.setStudentId(1);
		Student dbs=new StudentDao().getOne(s);
		System.out.println(dbs.toString());
		assertTrue(dbs!=null);
		
	}
	@Test
	void studentGetGrades() {
		Student s=new Student();
		s.setStudentId(1);
		List<Map<String,Object>> selectGrade = new StudentDao().selectGrade(1, 1);
		System.out.println(selectGrade.toString());
		assertTrue(selectGrade.size()>0);
		
	}
	
	@Test
	void passwordTest() {
		try {
			System.out.println(check("Test123","XOnPiuUCGEVfCa8T355R5Vu6gUHK6kQ6SWXwdXR7bO8=$cj9akCA/vPghqQW5Szfdw0YjUkknARK0afevPvF161M="));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public static String getSaltedHash(String password) throws Exception {
	        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
	        // store the salt with the password
	        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
	    }
	 
	 public static boolean check(String password, String stored) throws Exception{
	        String[] saltAndHash = stored.split("\\$");
	        if (saltAndHash.length != 2) {
	            throw new IllegalStateException(
	                "The stored password must have the form 'salt$hash'");
	        }
	        String hashOfInput = hash(password, Base64.decodeBase64(saltAndHash[0]));
	        return hashOfInput.equals(saltAndHash[1]);
	    }

	    // using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt
	    // cf. http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
	    private static String hash(String password, byte[] salt) throws Exception {
	        if (password == null || password.length() == 0)
	            throw new IllegalArgumentException("Empty passwords are not supported.");
	        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	        SecretKey key = f.generateSecret(new PBEKeySpec(
	            password.toCharArray(), salt, iterations, desiredKeyLen));
	        return Base64.encodeBase64String(key.getEncoded());
	    }

}
