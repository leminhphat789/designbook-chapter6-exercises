# Bài 6.17 — Redo

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
