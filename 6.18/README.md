# Bài 6.18 — Đóng gói processor trong Program

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
