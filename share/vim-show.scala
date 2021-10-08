// let's get it


// MOVEMENT - 移动


// 要习惯`hjkl`，废弃小键盘的方向


/*
            ↑
            k
     ← h         l →
            j
            ↓
*/

// `:w`，`:q`，`:wq`，`:x`，`:q!` - 学会保存/退出

// 选择单词 `w`和`b`

// 编辑

// 可以用`$`移动到行尾

// 敲`a`在光标处append

// 可以敲`A`，直接在行尾append

// 在`Insert`模式完成后，记得敲`ESC`

// `x` -> 删除选中的字符

// `d` -> 删除命令

// `dd` -> 删除一行

// `D` -> 删除光标到行尾


// 查找 `/username` + `Enter`
// `ci)` 替换整个参数

case class Token(secret: String) {
  def apply() = ???
  def unapply(): Unit = {
    ???
  }
}

def login(username: String, password: String) = {
   if (isValid(username, password)) println("succeed")
}

def isValid(username: String, password: String) = true
def token(token: Token) = true


// VISUAL MODE

// `Vjj`，选中代码块，注释/取消注释
// `vip`

// `zc`, `zo`, `折叠代码块

def comment() = {
   println("-comment-")
}


// SEARCH & REPLACE

// `/` 开始查找, `/let`
// `*` 查找光标选中的内容
// `n` 下一个，`N` 上一个

// 插件：easy-motion
// 插件：surround

// `V` + `jjj`
// :s/\d\"\0"/ 使用正则

// `Ctrl+v`, `jj`，选中列


var zzone = "1";
var zztwo = "2";
var zzthree = "3";
val four = "4";
