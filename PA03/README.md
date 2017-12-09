# 422Project
### 9/13 Figuring out what Lexical Checks to be done
- Useless Type Indication (Sarah)
- Whole Part (Sarah)
- Meaningless Terms (Michael)
- Overloaded Identifiers (Michael)
- Misspelling (Declan)
- Extreme Contraction (Declan)

# Deliverable 1 Report

### Meaningless Terms – Michael Duncan
Description: Finds all method, variable, parameter, class, and interface names that are meaningless (e.x. “foo”, “bar”, “var”)
How it works: The user enters a comma separated list of meaningless terms when setting up the check. This method was chosen because some common meaningless “filler” terms such as “bar” are not meaningless depending on the context, so a dictionary check would not be sufficient.
Quality: This test is high quality as it will find all meaningless terms given. Unit tests are thorough, and system and integration tests are as good as they can be for the limited public methods and unclear system requirements.

### Overloaded Identifiers – Michael Duncan
Description: Finds methods that have more than one verb in the name (e.x. getFileAndRead) or class, variable, parameter, or interface names with more than one noun in the name (e.x. ArcticEnemyPlayer). 
How it works: Methods, classes, variables, parameters, or interfaces that break the criteria outlined above are flagged for having too many nouns or too many verbs. This uses a POS tagger from the Stanford NLP library. Since variable names are not sentences, however, the tagger is far from perfect and tags some terms incorrectly. I used the best free NLP parser I could, so I will consider this as best as it can be for now. 
Quality: This test is as high quality as I could get it given the limitation of the tagger. Unit tests are thorough, and system and integration tests are as good as they can be for the limited public methods and unclear system requirements.

### Whole Part - Sarah Hughes
Decription: Finds all method and variable definitions with identifiers that contain the identifier of the parent class
How it works: The check is run automatically when the code is checked. It visits each method_def and variable_def token, pulls the identifier, and traverses up the tree until the parent class definition is found.
Quality: This check finds all method/variable definitions containing parent class identifiers regardless of case. The unit tests are thorough, with adequate system and integration tests for the methods used.

### Useless Type Indication - Sarah Hughes
Description: Finds all variable definitions with identifiers that contain the name of the data type.
How it works: The check is run automatically when the code is checked. It maps the data type to a string value, and determines whether that string is contained in the variable's identifier.
Quality: The check finds all variable defintions that contain the data type in the identifier. It is not case sensitive, and also checks for common shortened types like "str" and "bool". Because of the DetailAST tree structure, the strings had to be mapped to the data type manually, but the method was thorough and effective. All methods were unit tested, and methods that interact were integration tested.

### Misspelled Words - Declan Morita
Description: Find all words in a method definition that are Misspelled in their names.
How it works: The check runs automatically when the code is checked. It parses the name of the Method into substring to then be check against a Hashset of strings that are words fromt he English Dictionary. 
Quality: The check finds all variables definitions that can be parsed into substrings of possible words based on the placements of capitol letters. All methods that were united tested were properly evaluated and met required completetion status's. Further testing is possible if requested.

### Extreme Contraction - Declan Morita
Description: Find all possible contrations for a given Method Definition. Then determine if those contractions are indeed proper contractions.
How it works: Checks all method definitions and parses them into a substrings. By checking all substrings with lenght less than 3 we then check to see if those given substrings are either correctly spelled words or commonly used contrations.  If not then throw a warning at the user.
Quailty: This check finds all method definitons and determines if any existant contractions are proper and readable. By comparing the substrings to a Dictionary of short words and actual contractions we can determine if the substring is proper. The unit tests are thorough, with adequate system and integration tests for the methods used.  Further testing is possible if requested.