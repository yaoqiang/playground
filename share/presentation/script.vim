set nolist
set nonumber
set nofoldenable
set nocursorcolumn
set nocursorline
set colorcolumn=0
if exists('+relativenumber')
  set norelativenumber
end
set hidden


noremap <PageUp> :bp<CR>
noremap <Left> :bp<CR>
noremap <PageDown> :bn<CR>
noremap <Right> :bn<CR>
noremap Q :q<CR>

b 1
b 2
b 3
call matchadd("Comment", "\\n        · Insert\\n        · Visual\\(\\_.*slide 003\\)\\@=")
b 4
call matchadd("Comment", "· Normal\\(\\_.*slide 004\\)\\@=")
call matchadd("Comment", "· Visual\\(\\_.*slide 004\\)\\@=")
b 5
call matchadd("Comment", "· Normal\\(\\_.*slide 005\\)\\@=")
call matchadd("Comment", "· Insert\\(\\_.*slide 005\\)\\@=")
b 6
b 7
8,24SyntaxInclude javascript
b 8
b 1
