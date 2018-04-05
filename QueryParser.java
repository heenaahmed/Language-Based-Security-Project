package com.example.contactstealerblock;

public class QueryParser {
	public int getArgumentCount(String query){
		int count=0;
		boolean flag = true;
		for(int pos=0;pos<query.length();pos++){
			if(flag && query.charAt(pos) == '=') flag = false;
			
			else if(!flag && (query.charAt(pos) == '&' || pos == query.length()-1)){
				count++;
				flag = true;
			}
		}
		return count;
	}
	
	public boolean validArgument(String arg){
		
		return true;
	}
}
