package ru.a5x5retail.frontproductmanagement.help;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import ru.a5x5retail.frontproductmanagement.R;

public class HelpDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialogfragment_help, container);
        init(view);
        return view;
    }

    private TextView helpTextView_1,ok;

    private void init(View view){
        getDialog().setTitle("Справка:");

        helpTextView_1 = view.findViewById(R.id.helpTextView_1);
        helpTextView_1.setText(getTextFor(activityClass));

        ok = view.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private Class<?> activityClass;

    public void showFor(FragmentManager fm, Class<?> activityClass){
        this.activityClass = activityClass;
        show(fm,"tag");
    }

    private String getTextFor(Class<?> activityClass){
        return  "У лукоморья дуб зелёный;\n" +
                "Златая цепь на дубе том:\n" +
                "И днём и ночью кот учёный\n" +
                "Всё ходит по цепи кругом;\n" +
                "Идёт направо - песнь заводит,\n" +
                "Налево - сказку говорит.\n" +
                "Там чудеса: там леший бродит,\n" +
                "Русалка на ветвях сидит;\n" +
                "Там на неведомых дорожках\n" +
                "Следы невиданных зверей;\n" +
                "Избушка там на курьих ножках\n" +
                "Стоит без окон, без дверей;\n" +
                "Там лес и дол видений полны;\n" +
                "Там о заре прихлынут волны\n" +
                "На брег песчаный и пустой,\n" +
                "И тридцать витязей прекрасных\n" +
                "Чредой из вод выходят ясных,\n" +
                "И с ними дядька их морской;\n" +
                "Там королевич мимоходом\n" +
                "Пленяет грозного царя;\n" +
                "Там в облаках перед народом\n" +
                "Через леса, через моря\n" +
                "Колдун несёт богатыря;\n" +
                "В темнице там царевна тужит,\n" +
                "А бурый волк ей верно служит;\n" +
                "Там ступа с Бабою Ягой\n" +
                "Идёт, бредёт сама собой,\n" +
                "Там царь Кащей над златом чахнет;\n" +
                "Там русский дух... там Русью пахнет!\n" +
                "И там я был, и мёд я пил;\n" +
                "У моря видел дуб зелёный;\n" +
                "Под ним сидел, и кот учёный\n" +
                "Свои мне сказки говорил.";
    }
}
