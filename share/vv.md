# VIM

· 神一样的编辑器

· vi是vim的祖先

· NeoVim是vim的`友商`

· vim可以语法高亮, 插件, 脚本等等

· 第一次vi/vim经历 = 重启?


# GOAL

· 省点鼠标钱

· 效率++

· with IDEA / Visual Studio Code ...

· 假装是一个hacker

· 你的.vimrc

· more


# MODE

· Normal{~
· Insert
· Visual~}


# MODE

{~· Normal~}
· Insert
{~· Visual~}


# MODE

{~· Normal~}
{~· Insert~}
· Visual


# Grammar Rule

· verb + noun

· 名词（动作）

h    Left
j    Down
k    Up
l    Right
w    Move forward to the beginning of the next word
}    Jump to the next paragraph
$    Go to the end of the line

· 动词（操作）`:h operator`

y    Yank text (copy)
d    Delete text and save to register
c    Delete text, save to register, and start insert mode


## CODE

Javascript style

```javascript
class Car {
  constructor(name, year) {
    this.name = name;
    this.year = year;
  }
  age(x) {
    return x - this.year;
  }
}

let date = new Date();
let year = date.getFullYear();

let myCar = new Car("Ford", 2014);
document.getElementById("demo").innerHTML=
"My car is " + myCar.age(year) + " years old.";
```


# The End!

