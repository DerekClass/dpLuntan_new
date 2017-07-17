package com.Derek.dpLuntan;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public class SensitiveTest {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveTest.class);

    private class TrieNode {

        //敏感词终结点
        private boolean end = false;
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        public TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        public void setEndWord(boolean end) {
            this.end = end;
        }

        public boolean isEndWord() {
            return end;
        }
    }

    private boolean isSymbol(char c) {
        int ic = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    private TrieNode rootNode = new TrieNode();

    private void addTrieNode(String lineword) {
        TrieNode root = rootNode;
        for (int i = 0; i < lineword.length(); i++) {
            Character c = lineword.charAt(i);
            if (isSymbol(c)) {
                continue;
            }
            TrieNode node = root.getSubNode(c);
            if (node == null) {
                node = new TrieNode();
                root.addSubNode(c, node);
            }
            root = node;

            if (i == lineword.length() - 1) {
                root.setEndWord(true);
            }
        }
    }

    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String replacement = "***";
        StringBuilder stringBuilder = new StringBuilder();
        TrieNode root = rootNode;
        int begin = 0;
        int position = 0;
        while (position < text.length()) {
            char c = text.charAt(position);
            if (isSymbol(c)) {
                if (root ==rootNode) {
                    stringBuilder.append(c);
                }
                position++;
                continue;
            }
            root = root.getSubNode(c);
            if (root == null) {
                stringBuilder.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                root = rootNode;
            }
            else if (root.isEndWord()) {
                stringBuilder.append(replacement);
                begin = position + 1;
                position = begin;
                root = rootNode;
            } else {
                ++position;
            }
        }
        stringBuilder.append(text.substring(begin));
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        SensitiveTest st = new SensitiveTest();
        st.addTrieNode("色情");
        st.addTrieNode("嫖娼");
        System.out.println(st.filter(" 色 情网站嫖￥娼信息嫖"));
    }
}
