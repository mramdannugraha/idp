package com.ramdan.trainingkaryawan.utils.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfiguration {
    //    https://rizkimufrizal.github.io/belajar-spring-async/
    /*
    https://stackoverflow.com/questions/17659510/core-pool-size-vs-maximum-pool-size-in-threadpoolexecutor
    https://www.baeldung.com/java-threadpooltaskexecutor-core-vs-max-poolsize
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();
        return executor;
    }
    /*
    Code diatas berfungsi untuk mendefinisikan bean asyncTaskExecutor,
     dimana penulis membuat sebuah thread pool dengan prefix default_task_executor_thread,
     sehingga nanti nya kita dapat menggunakan prefix tersebut untuk memanggil
      thread pool nya, contoh penggunaan nya yaitu default_task_executor_thread-test atau default_task_executor_thread-sample.

     Aturan untuk ukuran ThreadPoolExecutor'skolam umumnya salah dipahami, karena tidak bekerja seperti yang Anda pikirkan seharusnya atau dengan cara yang Anda inginkan.

Ambil contoh ini. Ukuran kumpulan utas awal adalah 1, ukuran kumpulan inti adalah 5, ukuran kumpulan maks adalah 10 dan antrian adalah 100.

Sun's way: saat request masuk thread akan dibuat hingga 5, kemudian tugas akan ditambahkan ke antrian hingga mencapai 100. Ketika antrian penuh akan dibuat thread baru hingga maxPoolSize. Setelah semua utas digunakan dan antrean penuh, tugas akan ditolak. Saat antrian berkurang, begitu juga jumlah utas aktif.

Cara yang diantisipasi pengguna: saat permintaan masuk dalam utas akan dibuat hingga 10, maka tugas akan ditambahkan ke antrean hingga mencapai 100 pada titik mana mereka ditolak. Jumlah utas akan diganti namanya secara maksimal hingga antrian kosong. Ketika antrian kosong, utas akan mati sampai ada yang corePoolSizetersisa.

Perbedaannya adalah pengguna ingin mulai meningkatkan ukuran kolam lebih awal dan ingin antrian menjadi lebih kecil, sedangkan metode Sun ingin menjaga ukuran kolam tetap kecil dan hanya menambahnya setelah beban menjadi banyak.

Berikut adalah aturan Sun untuk pembuatan utas secara sederhana:

Jika jumlah utas kurang dari corePoolSize, buat utas baru untuk menjalankan tugas baru.
Jika jumlah utas sama (atau lebih besar dari) corePoolSize, masukkan tugas ke dalam antrian.
Jika antrian penuh, dan jumlah utas kurang dari maxPoolSize, buat utas baru untuk menjalankan tugas.
Jika antrian penuh, dan jumlah utas lebih besar dari atau sama dengan maxPoolSize, tolak tugas. Panjang dan pendeknya adalah bahwa utas baru hanya dibuat ketika antrian terisi, jadi jika Anda menggunakan antrian tidak terbatas maka jumlah utas tidak akan melebihi corePoolSize.
Untuk penjelasan lebih lengkap, dapatkan dari mulut kuda: ThreadPoolExecutordokumentasi API.

Ada posting forum yang sangat bagus yang memberi tahu Anda cara ThreadPoolExecutorkerjanya dengan contoh kode: http://forums.sun.com/thread.jspa?threadID=5401400&tstart=0

Info lebih lanjut: http://forums.sun.com/thread.jspa?threadID=5224557&tstart=450
     */
}
