package com.uta.sp.dto;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;

public class User {

	@Override
	public String toString() {
		return "User [userID=" + userID + ", roleId=" + roleId + ", professorId=" + professorId + ", studentId="
				+ studentId + ", loginAttepmts=" + loginAttepmts + ", userName=" + userName + ", password=" + password
				+ "]";
	}

	private int userID, roleId, professorId, studentId, loginAttepmts;
	private String userName, password;
	private static final int iterations = 20 * 1000;
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getLoginAttepmts() {
		return loginAttepmts;
	}

	public void setLoginAttepmts(int loginAttepmts) {
		this.loginAttepmts = loginAttepmts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean check(User clientUserObject) throws Exception {
		String[] saltAndHash = password.split("\\$");
		if (saltAndHash.length != 2) {
			throw new IllegalStateException("The stored password must have the form 'salt$hash'");
		}
		String hashOfInput = hash(clientUserObject.getPassword(), Base64.decodeBase64(saltAndHash[0]));
		return hashOfInput.equals(saltAndHash[1]);
	}



	private String hash(String password, byte[] salt) throws Exception {
		if (password == null || password.length() == 0)
			throw new IllegalArgumentException("Empty passwords are not supported.");
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
		return Base64.encodeBase64String(key.getEncoded());
	}

}
