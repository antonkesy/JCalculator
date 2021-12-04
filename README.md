# JCalculator

## Grammar

**E**xpression</br>
**T**erm</br>
**F**actor</br>

```
E -> T + E | T - E | T
T -> F * T | F / T | F
F -> Constant | Integer | (E) | -F 
```