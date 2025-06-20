document.getElementById("favoriteBtn").addEventListener("click", function () {
    const star = document.getElementById("starIcon");
    const msg = document.getElementById("responseMsg");

    if (star.textContent === "☆") {
      star.textContent = "★"; // 星を塗る

    // メッセージを表示
    msg.classList.add("show");

    // 3秒後に非表示に戻す
    setTimeout(() => {
        msg.classList.remove("show");
    }, 3000);
    } else {
        star.textContent = "☆"; // 元に戻す
        msg.classList.remove("show");
    }
});
