package com.leoops.blockchain.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MerkleTree {
	
    private List<String> txIds;
    
    public String root;
    
    public MerkleTree(List<String> txIds) {
        this.txIds = txIds;
    }

    public void builderTree() {

        List<String> tempTxList = new ArrayList<String>();

        for (int i = 0; i < this.txIds.size(); i++) {
            tempTxList.add(this.txIds.get(i));
        }

        List<String> newTxList = getNewTxList(tempTxList);

        //执行循环，直到只剩下一个hash值
        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }

        this.root = newTxList.get(0);
    }

    /**
     * return Node Hash List.
     * @param tempTxList
     * @return
     */
    private List<String> getNewTxList(List<String> tempTxList) {

        List<String> newTxList = new ArrayList<String>();
        int index = 0;
        while (index < tempTxList.size()) {
            // left
            String left = tempTxList.get(index);
            index++;
            // right
            String right = "";
            if (index != tempTxList.size()) {
                right = tempTxList.get(index);
            }
            // sha2 hex value
            String sha2HexValue = getSHA2HexValue(left + right);
            newTxList.add(sha2HexValue);
            index++;

        }

        return newTxList;
    }

    /**
     * Return hex string
     * @param str
     * @return
     */
    public String getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for(byte b: cipher_byte) {
                sb.append(String.format("%02x", b&0xff) );
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
    	MerkleTree merkleTree = new MerkleTree(Arrays.asList("1fa3246fd65526f3757d735fa05dccdd7bc54ca83407850d1853fd7c415efadc"));
    	merkleTree.builderTree();
    	System.out.println(merkleTree.root);
    }
}
