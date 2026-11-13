# Bài 6.15 — Lệnh có thể hoàn tác

## Chạy

Cần JDK 17 trở lên. Mở terminal trong folder `6.15` và chạy:

```powershell
javac -encoding UTF-8 -d build *.java
java -cp build Main
```

Folder này độc lập; không cần mã trong các folder khác. Mỗi lớp/interface/enum có file Java riêng. `Main.java` chỉ chứa chương trình chạy thử và kiểm tra.

## Yêu cầu và cách làm

Phát triển lệnh của bài 6.14 để có thể hoàn tác. Client thêm hai phim, xóa một phim, xóa toàn bộ lịch, rồi hoàn tác từng thao tác theo thứ tự ngược.

- `Program` quản lý lịch bảy ngày. Ngày trống chứa `NullShow`.
- `Command` định nghĩa `execute()` và `undo()`.
- `Commands.add`, `Commands.remove`, `Commands.clear` là các factory. Chúng dùng `changing()` để tạo lệnh bằng anonymous class, giữ cách làm của bài 6.14.
- Mỗi lệnh lưu bản sao lịch ngay trước khi chạy. `undo()` khôi phục bản sao này.
- `Movie.copy()` và `NullShow.copy()` tạo đối tượng mới; `Program.get()` cũng trả về bản sao.

Ví dụ thêm phim B đè lên phim A: undo phải khôi phục A, không chỉ xóa B. Snapshot giải quyết cả trường hợp ghi đè và xóa toàn bộ. Với lịch bảy ngày, lưu cả lịch giúp cách triển khai đơn giản; có thể tối ưu lệnh thêm/xóa bằng cách lưu riêng ngày bị thay đổi.

## Thực hành

Đọc `exercise15()`. Client giữ bốn lệnh `addA`, `addB`, `removeA`, `clear`, thực thi rồi gọi `undo()` theo thứ tự ngược. Sau mỗi bước, `state()` kiểm tra đủ bảy ngày và in hai ngày có phim.

| Thao tác | Thứ Hai | Thứ Ba |
|---|---|---|
| Thêm A | A | Trống |
| Thêm B | A | B |
| Xóa A | Trống | B |
| Clear | Trống | Trống |
| Undo clear | Trống | B |
| Undo xóa A | A | B |
| Undo thêm B | A | Trống |
| Undo thêm A | Trống | Trống |

Bài này chưa có `CommandProcessor`. Client phải tự bảo đảm hoàn tác theo thứ tự ngược và không sửa lịch ngoài các lệnh đang theo dõi.

Kết quả mong đợi: `PASS: 56 checks`.

## Các file trong folder

- `Command.java`: Interface execute và undo.
- `Commands.java`: Factory tạo các lệnh bằng anonymous class.
- `Day.java`: Enum các ngày trong tuần.
- `Main.java`: Client minh họa và các kiểm tra kết quả.
- `Movie.java`: Dữ liệu phim và sao chép phim.
- `NullShow.java`: Biểu diễn ngày không có buổi diễn.
- `Program.java`: Quản lý lịch biểu diễn.
- `Show.java`: Interface cho buổi biểu diễn, có description và copy.
