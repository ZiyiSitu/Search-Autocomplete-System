package autocompletesystem;

import java.util.*;

public class AutocompleteSystem {
    class TrieNode {
        Map<Character, TrieNode> children;
        Map<String, Integer> counter; // sentences and hot degree
        boolean isWord;
        
        public TrieNode() {
            children = new HashMap<>();
            counter = new HashMap<>();
            isWord = false;
        }
    }
    
    class Trie {
        TrieNode root;
        
        public Trie() {
            root = new TrieNode();
        }
        
        // add a string s into trie, or increment its hot degree by 1
        public void add(String s, int times) {
            TrieNode cur = root;
            for (char c : s.toCharArray()) {
                if (!cur.children.containsKey(c)) {
                    cur.children.put(c, new TrieNode());
                }
                
                cur = cur.children.get(c);
                cur.counter.put(s, cur.counter.getOrDefault(s, 0) + times);
            }
            cur.isWord = true;
        }
        
        // find the top 3 hot sentences sorted by hot degree
        public List<String> find(String prefix) {
            TrieNode cur = root;
            for (char c : prefix.toCharArray()) {
                if (!cur.children.containsKey(c)) {
                    return new ArrayList<String>();
                }
                
                cur = cur.children.get(c);
            }
            
            Map<String, Integer> cnt = cur.counter;
            Queue<String> minheap = new PriorityQueue<>((a, b) -> {
                if (cnt.get(a) != cnt.get(b)) {
                    return cnt.get(a) - cnt.get(b);
                }
                return b.compareTo(a);
            });
            
            for (String sentence : cur.counter.keySet()) {
                minheap.offer(sentence);
                if (minheap.size() > 3) {
                    minheap.poll();
                }
            }
            
            List<String> res = new ArrayList<>();
            while (!minheap.isEmpty()) {
                res.add(minheap.poll());
            }
            Collections.reverse(res);
            
            return res;
        }
    }

    private Trie trie;
    private String prefix;

    // constructor, AutocompleteSystem initialization
    public AutocompleteSystem(String[] sentences, int[] times) {
        trie = new Trie();
        prefix = "";
        
        for (int i = 0; i < sentences.length; i++) {
            trie.add(sentences[i], times[i]);
        }
    }
    
    // read the next input c, and return the top 3 hot sentences sorted by 
    // hot degree
    public List<String> input(char c) {
        if (c == '#') {
            trie.add(prefix, 1);
            prefix = "";
            return new ArrayList<String>();
        }
        
        prefix += c;
        return trie.find(prefix);
    }
    
    public static void main(String[] args) {
        String[] sentences0 = 
        {"i love this game", "icecream", "i love coding", "iphone"};
        int[] times0 = {5, 3, 2, 1};
        
        AutocompleteSystem auto = new AutocompleteSystem(sentences0, times0);
        int testTimes = 3;
        Scanner sc = new Scanner(System.in);
        char c;
        
        for (int i = 0; i < testTimes; i++) {
            System.out.println("Test sentence " + (i + 1) + ": ");
            
            while (true) {
                String s = sc.nextLine();
                if (s == null || s.isEmpty()) {
                    continue;
                }
                
                c = s.charAt(0);
                List<String> sentences = auto.input(c);
                if (c == '#') {
                    break;
                }
                
                System.out.println("The top 3 sentences: ");
                for (String sentence : sentences) {
                    System.out.println(sentence);
                }
                System.out.println();
            }
        }
    }
    
}
