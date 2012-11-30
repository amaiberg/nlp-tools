/* Copyright (C) 2002 Univ. of Massachusetts Amherst, Computer Science Dept.
 This file is part of "MALLET" (MAchine Learning for LanguagE Toolkit).
 http://www.cs.umass.edu/~mccallum/mallet
 This software is provided under the terms of the Common Public License,
 version 1.0, as published by http://www.opensource.org.  For further
 information, see the file `LICENSE' included with this distribution. */


/**
 * 
 @author Ryan McDonald
 */

//package edu.umass.cs.mallet.users.ryantm.medline;
package edu.upenn.cis.taggers.malignancy;

import java.io.IOException;

import edu.umass.cs.mallet.base.fst.MultiSegmentationEvaluator;
import edu.umass.cs.mallet.base.fst.Transducer;
import edu.umass.cs.mallet.base.types.Instance;
import edu.umass.cs.mallet.base.types.InstanceList;
import edu.umass.cs.mallet.base.types.Sequence;

public class MalignancySegmentationEvaluator extends MultiSegmentationEvaluator
{
  String[] segmentStartTags;
  String[] segmentContinueTags;
  
  public MalignancySegmentationEvaluator (String[] segmentStartTags, String[] segmentContinueTags)
  {
    super(segmentStartTags,segmentContinueTags,false);
    this.segmentStartTags = segmentStartTags;
    this.segmentContinueTags = segmentContinueTags;
        
  }
  
  public String output (Transducer crf, InstanceList eval) throws IOException
  {
    StringBuffer toReturn = new StringBuffer();
    for (int i = 0; i < eval.size(); i++) {
      Instance instance = eval.getInstance(i);
      Sequence input = (Sequence) instance.getData();
      Sequence predOutput = crf.viterbiPath(input).output();
      String[] words = (String[])instance.getName();
      String[] types = new String[words.length];      
      for (int j = 0; j < predOutput.size(); j++) {
        types[j] = (String)predOutput.get(j);
        //System.out.print(types[j]+" | ");
      }
      
      for(int j = 0; j < words.length; j++) {
        boolean f = false;
        for(int s = 0; s < segmentStartTags.length; s++) {
          if(types[j].equals(segmentStartTags[s])) {
            f = true;
            toReturn.append("<"+segmentStartTags[s].substring(2,segmentStartTags[s].length())+">" + words[j]);
            for(int j1 = j+1; j1 < words.length && types[j1].equals(segmentContinueTags[s]); j1++) {
              toReturn.append(" " + words[j1]);
              j=j1;
            }
            toReturn.append("</"+segmentStartTags[s].substring(2,segmentStartTags[s].length())+">");
            break;
          }
        }
        if(!f)
          toReturn.append(words[j]);
        
        if(j < words.length-1)
          toReturn.append(" ");
      }
      toReturn.append("\n");
    }
    return toReturn.toString();
  }
  
}
