This project implements a search autocomplete system for a search engine.
Users input sentences. For each character of a sentence except '#', it returns
the top 3 historical hot sentences that have the prefix string already typed.

Rules:
1. The hot degree is the number of times the exceptly same sentence typed before.
2. The result should be sorted by hot degree. If two strings have the same hot
degree, then sorted by lexicographical order.

Key methods:
AutocompleteSystem(String[] sentences, int[] times):
	constructor, initialize a auto complete system.
	The input is historical data, an array of sentences, and the corresponding times 
	a sentence has been typed.
	
List<String> input(char c):
	The input c is the next character typed by the user. c could only be lower-case
	letter, blank space or a special character '#'. The previous typed sentences
	would be recorded in the system.
	The output will be the top 3 historical hot sentences that have the prefix string 
	already typed. 
	
Example:
String[] sentences = {"i love this game", "icecream", "i love coding", "iphone"};
int[] times = {5, 3, 2, 1};

input:('i')
output: ["i love this game", "icecream", "i love coding"]

input:(' ')
output: ["i love this game", "i love coding"]

input:('a')
output: []

