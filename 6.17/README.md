# Bài 6.17 — Redo

## Chạy

Cần JDK 17 trở lên. Mở terminal trong folder `6.17`:

```powershell
javac -encoding UTF-8 -d build *.java
java -cp build Main
```

Folder chứa đầy đủ mã nền, chạy độc lập. Mỗi lớp/interface/enum có file Java riêng. `Main.java` chỉ chứa chương trình chạy thử và kiểm tra.

## Phần phát triển từ bài 6.16

Thêm `redoStack` và `redoLast()` vào `CommandProcessor`.

| Thao tác | Cách xử lý |
|---|---|
| Execute lệnh mới | Chạy lệnh, đưa vào undoStack, xóa redoStack |
| Undo | Hoàn tác lệnh gần nhất, chuyển từ undoStack sang redoStack |
| Redo | Chạy lại lệnh gần nhất đã undo, chuyển từ redoStack sang undoStack |

Hai ngăn xếp đều private. Nếu không còn lệnh phù hợp, undo/redo trả về `false`. Lịch sử chỉ thay đổi sau khi thao tác tương ứng thành công.

## Vì sao cần xóa redo khi chạy lệnh mới?

Thêm A → thêm B → undo B → thêm C tạo một nhánh chỉnh sửa mới. B không còn thuộc chuỗi redo hiện tại. Vì vậy `execute()` xóa `redoStack`; riêng `redoLast()` gọi trực tiếp `command.execute()`, tránh gọi `processor.execute()` vì hàm đó sẽ xóa các lệnh redo còn lại.

Sau khi undo, lệnh đặt `applied = false`, cho phép execute lại. Khi redo, lệnh chụp trạng thái trước lần chạy lại để lần undo tiếp theo vẫn đúng.

## Thực hành

Đọc `exercise17()`. Chương trình kiểm tra:

- Undo/redo khi lịch sử rỗng.
- Thêm hai phim, xóa một phim, clear; undo hết rồi redo hết, kiểm tra lịch sau từng bước.
- Lệnh mới sau undo loại bỏ nhánh redo cũ.
- Lệnh thất bại không đi vào lịch sử và không làm mất redo đang có.

Mỗi thao tác mới phải dùng một đối tượng lệnh mới. Chỉ processor được thực thi/hoàn tác lệnh đã gửi vào nó; không thay đổi lịch trực tiếp trong lúc quản lý lịch sử. Các lệnh trong factory có khôi phục snapshot nếu thao tác phát sinh RuntimeException. Với lệnh tự viết khác, chính lệnh đó phải bảo đảm trạng thái dữ liệu khi thất bại.

Kết quả đã chạy: `PASS: 84 checks`.

## Các file trong folder

- `Command.java`: Interface execute và undo.
- `CommandProcessor.java`: Thực thi lệnh và quản lý lịch sử.
- `Commands.java`: Factory tạo các lệnh bằng anonymous class.
- `Day.java`: Enum các ngày trong tuần.
- `Main.java`: Client minh họa và các kiểm tra kết quả.
- `Movie.java`: Dữ liệu phim và sao chép phim.
- `NullShow.java`: Biểu diễn ngày không có buổi diễn.
- `Program.java`: Quản lý lịch biểu diễn.
- `Show.java`: Interface cho buổi biểu diễn, có description và copy.
