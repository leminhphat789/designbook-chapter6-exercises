# Bài 6.18 — Đóng gói processor trong Program

## Chạy

Cần JDK 17 trở lên. Mở terminal trong folder `6.18`:

```powershell
javac -encoding UTF-8 -d build *.java
java -cp build Main
```

Folder chứa đầy đủ mã nền, chạy độc lập. Mỗi lớp/interface/enum có file Java riêng. `Main.java` chỉ chứa chương trình chạy thử và kiểm tra.

## Phần phát triển từ bài 6.17

Client gọi trực tiếp `Program.add`, `remove`, `clear`, `undoLast`. `Program` giữ riêng một `CommandProcessor` và chuyển mỗi thao tác thay đổi lịch thành lệnh. Lời giải cũng giữ `redoLast()` của bài trước.

```java
Program program = new Program();
program.add(A, Day.MONDAY);
program.remove(Day.MONDAY);
program.undoLast(); // Khôi phục A
program.redoLast(); // Xóa A lần nữa
```

## Tránh God class bằng cách chia trách nhiệm

| Lớp | Trách nhiệm |
|---|---|
| Schedule | Quản lý dữ liệu lịch, sao chép và khôi phục |
| Commands | Tạo lệnh anonymous class, lưu trạng thái để undo |
| CommandProcessor | Quản lý hai ngăn xếp và thứ tự thực thi/undo/redo |
| Program | Cung cấp API cho client và điều phối các thành phần |

Ở các bài trước, lớp quản lý dữ liệu lịch tên là `Program`. Sang bài này, trách nhiệm đó được chuyển sang `Schedule`; `Program` trở thành lớp giao tiếp với client. Đây là composition và delegation: Program có các thành phần chuyên trách và giao việc cho chúng.

`Program` không tự push/pop ngăn xếp, và cũng không trả processor hoặc lịch nội bộ ra ngoài. Chẳng hạn `add` chỉ gọi:

```java
processor.execute(Commands.add(schedule, show, day));
```

Lệnh sửa trực tiếp `Schedule`, không gọi ngược `Program.add()`. Nếu gọi ngược, thao tác có thể tiếp tục tạo lệnh mới và dẫn đến đệ quy. Mỗi lần gọi add/remove/clear tạo một lệnh mới, bảo đảm không tái sử dụng sai đối tượng trong lịch sử.

## Thực hành

Đọc `exercise18()`. Client chỉ thao tác qua Program. Kiểm tra ghi đè A bằng B rồi undo về A, redo về B; xóa và khôi phục; clear rồi khôi phục phim ở cả thứ Hai và Chủ nhật; redo clear; và kiểm tra `get()` trả bản sao riêng cho cả Movie và NullShow.

Đây là bản minh họa tối thiểu của hệ thống Show: Movie và NullShow đủ để thực hành bốn bài về Command. Khi tích hợp các kiểu Show của bài trước, phương thức `copy()` của mỗi kiểu cần bảo đảm sao chép sâu.

Kết quả đã chạy: `PASS: 24 checks`.

## Các file trong folder

- `Command.java`: Interface execute và undo.
- `CommandProcessor.java`: Thực thi lệnh và quản lý lịch sử.
- `Commands.java`: Factory tạo các lệnh bằng anonymous class.
- `Day.java`: Enum các ngày trong tuần.
- `Main.java`: Client minh họa và các kiểm tra kết quả.
- `Movie.java`: Dữ liệu phim và sao chép phim.
- `NullShow.java`: Biểu diễn ngày không có buổi diễn.
- `Program.java`: API cho client, ủy quyền cho Schedule và CommandProcessor.
- `Schedule.java`: Quản lý dữ liệu lịch, snapshot và restore.
- `Show.java`: Interface cho buổi biểu diễn, có description và copy.
