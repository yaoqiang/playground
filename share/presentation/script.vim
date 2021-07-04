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
call matchadd("Comment", "\\n        • Insert\\n        • Visual\\(\\_.*slide 003\\)\\@=")
b 4
call matchadd("Comment", "• Normal\\(\\_.*slide 004\\)\\@=")
call matchadd("Comment", "• Visual\\(\\_.*slide 004\\)\\@=")
b 5
call matchadd("Comment", "• Normal\\(\\_.*slide 005\\)\\@=")
call matchadd("Comment", "• Insert\\(\\_.*slide 005\\)\\@=")
b 6
b 7
call matchadd("Comment", "• First\\(\\_.*slide 007\\)\\@=")
call matchadd("Comment", "\\n        • Third\\(\\_.*slide 007\\)\\@=")
b 8
call matchadd("Comment", "• First\\n        • Second\\(\\_.*slide 008\\)\\@=")
b 9
1,1SyntaxInclude scala
b 10
6,17SyntaxInclude ruby
b 11
6,17SyntaxInclude ruby
call matchadd("Comment", "module Parts\\n          class foo\\n            def slide\\n             \"of a\"\\n            end\\n\\n            def can\\n              highlight = \\(\\_.*slide 011\\)\\@=")
call matchadd("Comment", "\\n            end\\n          end\\n        end\\(\\_.*slide 011\\)\\@=")
b 12
6,17SyntaxInclude ruby
call matchadd("Comment", "module Parts\\n          class foo\\n            def slide\\n             \"of a\"\\n            end\\n\\n            def\\(\\_.*slide 012\\)\\@=")
call matchadd("Comment", "highlight = \"vimdeck\"\\n            end\\n          end\\n        end\\(\\_.*slide 012\\)\\@=")
b 13
6,17SyntaxInclude ruby
call matchadd("Comment", "module Parts\\n          class foo\\n            def slide\\n             \"of a\"\\n            end\\n\\n            def can\\(\\_.*slide 013\\)\\@=")
call matchadd("Comment", "= \"vimdeck\"\\n            end\\n          end\\n        end\\(\\_.*slide 013\\)\\@=")
b 14
6,17SyntaxInclude ruby
call matchadd("Comment", "module\\(\\_.*slide 014\\)\\@=")
call matchadd("Comment", "class foo\\n            def slide\\n             \"of a\"\\n            end\\n\\n            def can\\n              highlight = \"vimdeck\"\\n            end\\n          end\\n        end\\(\\_.*slide 014\\)\\@=")
b 15
6,17SyntaxInclude ruby
call matchadd("Comment", "module Parts\\n          class foo\\n            def slide\\(\\_.*slide 015\\)\\@=")
call matchadd("Comment", "end\\n\\n            def can\\n              highlight = \"vimdeck\"\\n            end\\n          end\\n        end\\(\\_.*slide 015\\)\\@=")
b 16
6,17SyntaxInclude ruby
call matchadd("Comment", "module Parts\\n          class foo\\n            def\\(\\_.*slide 016\\)\\@=")
call matchadd("Comment", "\"of a\"\\n            end\\n\\n            def can\\n              highlight = \"vimdeck\"\\n            end\\n          end\\n        end\\(\\_.*slide 016\\)\\@=")
b 17
6,16SyntaxInclude javascript
b 18
6,16SyntaxInclude javascript
call matchadd("Comment", "(function( window, $, undefined ) {\\n            $( '.hello' ).on( 'click', function sayHello() {\\(\\_.*slide 018\\)\\@=")
call matchadd("Comment", "\\n            });\\n        })( window, jQuery );\\(\\_.*slide 018\\)\\@=")
b 19
6,16SyntaxInclude javascript
call matchadd("Comment", "<body>\\(\\_.*slide 019\\)\\@=")
call matchadd("Comment", "</body>\\(\\_.*slide 019\\)\\@=")
b 20
6,16SyntaxInclude javascript
call matchadd("Comment", "(function( window, $, undefined ) {\\n            $( '.hello' ).on( 'click', function sayHello() {\\(\\_.*slide 020\\)\\@=")
call matchadd("Comment", "\\n            });\\n        })( window, jQuery );\\n\\n        <body>\\n            <a href=\"#\" class=\"hello\">Hello!</a>\\n        </body>\\(\\_.*slide 020\\)\\@=")
b 21
b 1
