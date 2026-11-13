# Bài 6.16 — CommandProcessor và Law of Demeter

## Phần phát triển từ bài 6.15

Giữ `Program` quản lý lịch và các lệnh anonymous class có snapshot để undo. Thêm `CommandProcessor` với một ngăn xếp riêng tư `undoStack`.

- `execute(command)`: thực thi lệnh, sau khi thành công mới đẩy vào ngăn xếp.
- `undoLast()`: lấy lệnh gần nhất, hoàn tác, sau khi thành công mới bỏ khỏi ngăn xếp.
- Nếu lịch sử rỗng, `undoLast()` trả về `false`.

Ví dụ client:

```java
processor.execute(Commands.add(schedule, A, Day.MONDAY));
processor.undoLast();
```

## Giải thích Law of Demeter

Client chỉ giao tiếp với processor qua hành vi `execute` và `undoLast`. Không có getter trả ngăn xếp, nên client không cần biết processor lưu lịch sử bằng cấu trúc nào và không thể tự `pop()` lịch sử. Processor gọi trực tiếp `execute()`/`undo()` trên lệnh mà nó quản lý.

Ngăn xếp là LIFO: lệnh chạy sau được hoàn tác trước. Client không phải giữ từng lệnh rồi tự quyết định thứ tự undo như bài 6.15.

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
