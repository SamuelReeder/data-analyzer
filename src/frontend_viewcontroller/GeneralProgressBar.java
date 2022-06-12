///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package frontend_viewcontroller;
//
//import backend_models.Python;
//import java.util.List;
//import javax.swing.JProgressBar;
//import javax.swing.SwingWorker;
//
///**
// *
// * @author samue
// */
//public class GeneralProgressBar extends SwingWorker<Void, Integer> {
//        
//        private static JProgressBar progress;
//        
//        public GeneralProgressBar(JProgressBar progress) {
//            this.progress = progress;
//        }
//        @Override
//        protected Void doInBackground() throws Exception {
//
//            int i = Python.getProgress();
//            while (i <= 100) {
//                publish(i);
////                progress.setValue(i);
//                Thread.sleep(100);
//                i = Python.getProgress();
//            }
//            return null;
//        }
//
//        @Override
//        protected void process(List<Integer> chunks) {
//              
//            progress.setValue(chunks.get(chunks.size() - 1));
//            super.process(chunks);
//        }
//
//        @Override
//        protected void done() {
//            progress.setValue(100);
//        }
//    }
