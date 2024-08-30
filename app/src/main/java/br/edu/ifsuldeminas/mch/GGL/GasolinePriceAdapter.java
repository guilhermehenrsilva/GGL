package br.edu.ifsuldeminas.mch.GGL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsuldeminas.mch.GGL.model.GasolinePrice;

public class GasolinePriceAdapter extends ArrayAdapter<GasolinePrice> {

    public GasolinePriceAdapter(Context context, List<GasolinePrice> gasolinePrices) {
        super(context, 0, gasolinePrices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obter o item de dados para essa posição
        GasolinePrice gasolinePrice = getItem(position);

        // Verificar se a view existente está sendo reutilizada, caso contrário, inflar a view
        if (convertView == null) {
            convertView = new LinearLayout(getContext());
            ((LinearLayout) convertView).setOrientation(LinearLayout.VERTICAL);
            int padding = (int) (16 * getContext().getResources().getDisplayMetrics().density);
            convertView.setPadding(padding, padding, padding, padding);
        }

        // Limpar qualquer conteúdo anterior
        ((LinearLayout) convertView).removeAllViews();

        // Criar os TextViews dinamicamente e adicionar ao LinearLayout
        TextView gasStationNameTextView = new TextView(getContext());
        gasStationNameTextView.setTextSize(18);
        gasStationNameTextView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        gasStationNameTextView.setText(gasolinePrice.getCountry());
        ((LinearLayout) convertView).addView(gasStationNameTextView);

        TextView gasolinePriceTextView = new TextView(getContext());
        gasolinePriceTextView.setTextSize(16);
        gasolinePriceTextView.setTextColor(getContext().getResources().getColor(android.R.color.darker_gray));
        gasolinePriceTextView.setText(String.format(gasolinePrice.getPrice()));
        ((LinearLayout) convertView).addView(gasolinePriceTextView);

        // Retornar a view completa para ser exibida na tela
        return convertView;
    }
}