package vitor.edu.br.malhagrafos.control.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import vitor.edu.br.malhagrafos.control.graphOp.Controls;
import vitor.edu.br.malhagrafos.model.Estrutura;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.graphRota.Edges;
import vitor.edu.br.malhagrafos.model.graphRota.Graph;
import vitor.edu.br.malhagrafos.model.graphVoos.GraphVoo;

/**
 *
 * @author vitor
 */
public class TableExport {
    
    private static HSSFWorkbook workbookRoute = new HSSFWorkbook();
    private static HSSFWorkbook workbookFly = new HSSFWorkbook();
    private static HSSFSheet sheetRoute = workbookRoute.createSheet("TableRoute");
    private static HSSFSheet sheetFly = workbookFly.createSheet("TableFly");
    private static int rownumR= 0;
    private static int cellnumR = 0;
    private static int rownumF= 0;
    private static int cellnumF = 0;
    private static Cell cellR;
    private static Row rowR;
    private static Cell cellF;
    private static Row rowF;

    public static void exportExcel(Estrutura estrutura){
        tableRoute(estrutura);
        tableFly(estrutura);
    }

    private static void tableRoute(Estrutura estrutura){
        styleConfigurationRoute(estrutura);
        gerarXLS("tableRoute",1);
    }

    private static void tableFly(Estrutura estrutura){
        styleConfigurationFly(estrutura);
        gerarXLS("tableFly",2);
    }



    private static void styleConfigurationRoute(Estrutura estrutura){

        // Definindo alguns padroes de layout
        sheetRoute.setDefaultColumnWidth(30);
        sheetRoute.setDefaultRowHeight((short) 650);
        //Configurando estilos de células (Cores, alinhamento, formatação, etc..)
        HSSFDataFormat numberFormat = workbookRoute.createDataFormat();

        CellStyle headerStyle = workbookRoute.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        CellStyle textStyle = workbookRoute.createCellStyle();
        textStyle.setAlignment(CellStyle.ALIGN_CENTER);
        textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        headerConfigurationRoute(headerStyle, textStyle, estrutura);


    }

    private static void styleConfigurationFly(Estrutura estrutura){

        // Definindo alguns padroes de layout
        sheetFly.setDefaultColumnWidth(30);
        sheetFly.setDefaultRowHeight((short) 650);
        //Configurando estilos de células (Cores, alinhamento, formatação, etc..)
        HSSFDataFormat numberFormat = workbookFly.createDataFormat();

        CellStyle headerStyle = workbookFly.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        CellStyle textStyle = workbookFly.createCellStyle();
        textStyle.setAlignment(CellStyle.ALIGN_CENTER);
        textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);


        headerConfigurationFly(headerStyle, textStyle, estrutura);


    }



    private static void headerConfigurationRoute(CellStyle headerStyle, CellStyle textStyle, Estrutura estrutura){

        // Configurando Header
        rowR = sheetRoute.createRow(rownumR++);
        cellR = rowR.createCell(cellnumR++);
        cellR.setCellStyle(headerStyle);
        cellR.setCellValue("Aeroporto Origem");

        cellR = rowR.createCell(cellnumR++);
        cellR.setCellStyle(headerStyle);
        cellR.setCellValue("Aeroporto Destino");

        addingDataRoute(textStyle,estrutura.getRotas());
    }

    private static void headerConfigurationFly(CellStyle headerStyle, CellStyle textStyle, Estrutura estrutura) {

        // Configurando Header
        rowF = sheetFly.createRow(rownumF++);
        cellF = rowF.createCell(cellnumF++);
        cellF.setCellStyle(headerStyle);
        cellF.setCellValue("Voo");

        cellF = rowF.createCell(cellnumF++);
        cellF.setCellStyle(headerStyle);
        cellF.setCellValue("Aeroporto Origem");

        cellF = rowF.createCell(cellnumF++);
        cellF.setCellStyle(headerStyle);
        cellF.setCellValue("Aeroporto Destino");

        cellF = rowF.createCell(cellnumF++);
        cellF.setCellStyle(headerStyle);
        cellF.setCellValue("Partida(hh:mm)");

        cellF = rowF.createCell(cellnumF++);
        cellF.setCellStyle(headerStyle);
        cellF.setCellValue("Chegada(hh:mm)");

        cellF = rowF.createCell(cellnumF++);
        cellF.setCellStyle(headerStyle);
        cellF.setCellValue("Duração(min)");

        cellF = rowF.createCell(cellnumF++);
        cellF.setCellStyle(headerStyle);
        cellF.setCellValue("Escala(s)");

        cellF = rowF.createCell(cellnumF++);
        cellF.setCellStyle(headerStyle);
        cellF.setCellValue("Distância(km)");

        addingDataFly(textStyle,estrutura.getVoos());

    }



    private static void addingDataRoute(CellStyle textStyle, Graph rotas){
        // Adicionando os dados da rota na planilha
        for (Edges arestas : rotas.getArestas()) {
            rowR = sheetRoute.createRow(rownumR++);
            cellnumR = 0;

            cellR = rowR.createCell(cellnumR++);
            cellR.setCellStyle(textStyle);
            cellR.setCellValue(arestas.getOrigem().getAeroporto().getAirportName()
                    +"("+arestas.getOrigem().getAeroporto().getAbreviation()+")");

            cellR = rowR.createCell(cellnumR++);
            cellR.setCellStyle(textStyle);
            cellR.setCellValue(arestas.getDestino().getAeroporto().getAirportName()
                    +"("+arestas.getDestino().getAeroporto().getAbreviation()+")");
        }

    }

    private static void addingDataFly(CellStyle textStyle, GraphVoo voos) {
        // Adicionando os dados do voo na planilha
        for ( Voo fly : voos.getArestas()) {
            rowF = sheetFly.createRow(rownumF++);
            cellnumF = 0;

            //nome voo
            cellF = rowF.createCell(cellnumF++);
            cellF.setCellStyle(textStyle);
            cellF.setCellValue(fly.getVoo());

            //aeroporto origem
            cellF = rowF.createCell(cellnumF++);
            cellF.setCellStyle(textStyle);
            cellF.setCellValue(fly.getOrigem().getAirportName()
                    +"("+fly.getOrigem().getAbreviation()+")");

            //aeroporto destino
            cellF = rowF.createCell(cellnumF++);
            cellF.setCellStyle(textStyle);
            cellF.setCellValue(fly.getDestino().getAirportName()
                    +"("+fly.getDestino().getAbreviation()+")");

            //partida
            cellF = rowF.createCell(cellnumF++);
            cellF.setCellStyle(textStyle);
            cellF.setCellValue(fly.getPartida().toString());

            //chegada
            cellF = rowF.createCell(cellnumF++);
            cellF.setCellStyle(textStyle);
            cellF.setCellValue(fly.getChegada().toString());

            //duracao
            cellF = rowF.createCell(cellnumF++);
            cellF.setCellStyle(textStyle);
            Controls c = new Controls();
            cellF.setCellValue(c.calculaDiferencaTempo(fly.getPartida(),fly.getChegada()));

           //escala
            cellF = rowF.createCell(cellnumF++);
            cellF.setCellStyle(textStyle);
            cellF.setCellValue(fly.getNumeroParadas());

            //distancia
            cellF = rowF.createCell(cellnumF++);
            cellF.setCellStyle(textStyle);
            cellF.setCellValue(fly.getDistancia());

        }
    }


    private static void gerarXLS(String name, int flag){
        try {
            try ( //Escrevendo o arquivo em disco
                    FileOutputStream out = new FileOutputStream(new File("C:\\Users\\patri\\Documents\\IFMG\\6_Periodo\\TrabalhoMalhaGrafos\\src\\main\\resources\\output\\"+name+".xls"))) {
                if(flag==1){
                    workbookRoute.write(out);
                }else{
                    workbookFly.write(out);
                }
            }
            System.out.println("Tabela "+name+" gerada.");
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
