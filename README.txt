Captures / grabs running Windows processes (via Wind 'tasklist' command, for peculiarities check https://ss64.com/nt/tasklist.html), 
creates excel sheet & draws Chart in Excel with the following data:
    +--------------------------+
    |    Name    | UsedMemory  |
    +--------------------------+
    | idea.exe   | 652         |
    | chrome.exe | 3704        |
    |svchost.exe | 293688      |
    +--------------------------+
Exports cleaned list of tasks to XML via XStream in the following format:
    <task>
        <name>chrome.exe</name>
        <pid>3600,2092,4388,3588,3944,2136,2104</pid>
        <usedMemory>681640</usedMemory>
    </task>