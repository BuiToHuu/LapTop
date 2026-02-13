# 1. Base Image: Sử dụng JRE 21 bản Alpine để nhẹ và bảo mật
FROM eclipse-temurin:21-jre-alpine

# 2. Tạo thư mục làm việc chính thức bên trong Container
WORKDIR /app

# 3. Sao chép file JAR từ thư mục target vào Container
# Sử dụng dấu * để tự động nhận diện file JAR bất kể tên dự án là gì
COPY target/*.jar app.jar

# 4. Tạo thư mục để ánh xạ ảnh từ ổ D (Khớp với docker-compose)
RUN mkdir -p /app/uploads

# 5. Lệnh chạy ứng dụng
# Cấu hình tối ưu bộ nhớ cho Java trong môi trường Docker
ENTRYPOINT ["java", "-Xmx512m", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]