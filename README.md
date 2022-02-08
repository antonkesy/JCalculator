# JCalculator

PEMDAS respecting calculator from scratch with a recursive descent operator-precedence parser in Java

## Usage

Create an Object of JCalculator and pass a INumberFactory which defines how and what numbers are computed.
Calling calculate returns result as string.

```java
String result = new JCalculator(new BigDecimalFactory()).calculate("1+1");
```

## Disclaimer

This is a learning project and not intended for real life calculations!
Purpose was to learn more about tokenizing/parsing instead of building a correct functioning calculator

## Problems

- Can't calculate with irrational numbers -> constants are just doubles
- Correct placement of parentheses are not checked
- and more ...
